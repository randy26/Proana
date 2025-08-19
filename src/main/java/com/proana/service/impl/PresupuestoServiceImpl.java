package com.proana.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.ClienteDto;
import com.proana.dto.ContactoDTO;
import com.proana.dto.DerivanteDTO;
import com.proana.dto.EmpleadoDTO;
import com.proana.dto.ItemDTO;
import com.proana.dto.MonedaDto;
import com.proana.dto.PresupuestoDTO;
import com.proana.dto.PresupuestoResumenDTO;
import com.proana.dto.UnidadNegocioDto;
import com.proana.dto.ViajeDTO;
import com.proana.exception.EntidadNoEncontradaException;
import com.proana.model.AbmDeterminacion;
import com.proana.model.AbmEstadoDeterminaciones;
import com.proana.model.AbmFti;
import com.proana.model.AbmUnidadDeterminacion;
import com.proana.model.Cliente;
import com.proana.model.Contacto;
import com.proana.model.Derivante;
import com.proana.model.Determinacion;
import com.proana.model.Empleado;
import com.proana.model.EstadoMuestra;
import com.proana.model.EstadoPresupuesto;
import com.proana.model.Matriz;
import com.proana.model.Moneda;
import com.proana.model.Muestra;
import com.proana.model.Presupuesto;
import com.proana.model.ReferenciaNormativa;
import com.proana.model.UnidadNegocio;
import com.proana.model.Viaje;
import com.proana.repository.PresupuestoRepository;
import com.proana.repository.UnidadNegocioRepository;
import com.proana.service.PresupuestoService;
import com.proana.utils.ProanaUtil;
import com.proana.utils.TypeConverter;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio para operaciones sobre Presupuestos.
 */
@Service
public class PresupuestoServiceImpl implements PresupuestoService {

	private static final Logger logger = LoggerFactory.getLogger(PresupuestoServiceImpl.class);

	@Autowired
	private PresupuestoRepository presupuestoRep;
	@Autowired
	private UnidadNegocioRepository unidadNegocioRep;

	/**
	 * Obtiene la lista completa de Presupuestos.
	 *
	 * @return lista de objetos {@link PresupuestoResumenDTO}
	 * @throws RuntimeException si ocurre un error durante la consulta a la base de
	 *                          datos
	 */
	@Override
	public List<PresupuestoResumenDTO> listarPresupuestos() {
		try {
			List<Object[]> rows = presupuestoRep.findPresupuestosResumen();

			return rows.stream()
					.map(row -> new PresupuestoResumenDTO(TypeConverter.toInteger(row[0]),
							TypeConverter.toStringSafe(row[1]), TypeConverter.toStringSafe(row[2]),
							TypeConverter.toDate(row[3]), TypeConverter.toInteger(row[4]), TypeConverter.toDate(row[5]),
							TypeConverter.toInteger(row[6]), TypeConverter.toDate(row[7]),
							TypeConverter.toStringSafe(row[8]), TypeConverter.toStringSafe(row[9]),
							TypeConverter.toLong(row[10]), TypeConverter.toBoolean(row[11])))
					.toList();
		} catch (Exception e) {
			logger.error("Error al obtener presupuestos", e);
			throw new RuntimeException("No se pudo obtener la lista de presupuestos", e);
		}
	}

	/**
	 * Guarda un nuevo presupuesto a partir de un DTO.
	 *
	 * @param dto Datos del presupuesto a guardar.
	 * @throws IllegalArgumentException     Si algún dato obligatorio es inválido o
	 *                                      faltante.
	 * @throws EntidadNoEncontradaException Si alguna entidad referenciada no existe
	 *                                      en la base.
	 */
	@Override
	public void guardarPresupuesto(final PresupuestoDTO dto) {
		Presupuesto presupuesto = new Presupuesto();

		presupuesto.setUnidadNegocio(mapUnidadNegocio(dto.getUnidadNegocio()));
		if (dto.isBpl()) {
			presupuesto.setBpl(0);
		} else {
			presupuesto.setBpl(1);
		}
		presupuesto.setTitulo(dto.getTitulo());
		presupuesto.setFechaPresupuesto(ProanaUtil.parseDateSql(dto.getFechaPresupuesto()));
		presupuesto.setValidezDelPresupuesto(parseInteger(dto.getValidezPresupuesto()));
		presupuesto.setFechaAceptacion(ProanaUtil.parseDateSql(dto.getFechaAceptacion()));
		presupuesto.setDuracionDelContrato(parseInteger(dto.getDuracionContrato()));
		presupuesto.setFechaInicio(ProanaUtil.parseDateSql(dto.getFechaInicio()));
		presupuesto.setOrdenDeCompra(dto.getOrdenCompra());
		presupuesto.setReferencia(dto.getReferencia());

		presupuesto.setCliente(mapCliente(dto.getCliente()));
		presupuesto.setMoneda(mapMoneda(dto.getMoneda()));
		presupuesto.setDerivante(mapDerivante(dto.getDerivante()));

		presupuesto.setComercial(mapEmpleado(dto.getComercial().getIdEmpleado(), "Comercial"));
		presupuesto.setResponsableContrato(mapEmpleado(dto.getResponsableContrato().getIdEmpleado(), "Responsable del Contrato"));

		presupuesto.setRevision(parseInteger(dto.getRevision()));

		presupuesto.setContacto(mapContacto(dto.getContacto()));

		EstadoPresupuesto estado = new EstadoPresupuesto();
		if (dto.getFacturacion().isModo()) {
			estado.setIdEstadoPresupuesto(1);
		} else {
			estado.setIdEstadoPresupuesto(2);
		}
		presupuesto.setEstadoPresupuesto(estado);
		//Mapear viajes
		presupuesto.setViajes(mapViajes(dto.getViajes(), presupuesto));
		// Mapear muestras
	    presupuesto.setMuestras(mapMuestras(dto.getItems(), presupuesto));
		presupuestoRep.save(presupuesto);
	}
	

	@Override
	public void actualizarPresupuesto(Integer id, PresupuestoDTO dto) {
		Presupuesto presupuesto = presupuestoRep.findById(id).
				orElseThrow(() -> new EntityNotFoundException("Presupuesto con ID " + id + " no encontrado"));
		presupuesto.setUnidadNegocio(mapUnidadNegocio(dto.getUnidadNegocio()));
		if (dto.isBpl()) {
			presupuesto.setBpl(0);
		} else {
			presupuesto.setBpl(1);
		}
		presupuesto.setTitulo(dto.getTitulo());
		presupuesto.setFechaPresupuesto(ProanaUtil.parseDateSql(dto.getFechaPresupuesto()));
		presupuesto.setValidezDelPresupuesto(parseInteger(dto.getValidezPresupuesto()));
		presupuesto.setFechaAceptacion(ProanaUtil.parseDateSql(dto.getFechaAceptacion()));
		presupuesto.setDuracionDelContrato(parseInteger(dto.getDuracionContrato()));
		presupuesto.setFechaInicio(ProanaUtil.parseDateSql(dto.getFechaInicio()));
		presupuesto.setOrdenDeCompra(dto.getOrdenCompra());
		presupuesto.setReferencia(dto.getReferencia());

		presupuesto.setCliente(mapCliente(dto.getCliente()));
		presupuesto.setMoneda(mapMoneda(dto.getMoneda()));
		presupuesto.setDerivante(mapDerivante(dto.getDerivante()));

		presupuesto.setComercial(mapEmpleado(dto.getComercial().getIdEmpleado(), "Comercial"));
		presupuesto.setResponsableContrato(mapEmpleado(dto.getResponsableContrato().getIdEmpleado(), "Responsable del Contrato"));

		presupuesto.setRevision(parseInteger(dto.getRevision()));

		presupuesto.setContacto(mapContacto(dto.getContacto()));

		EstadoPresupuesto estado = new EstadoPresupuesto();
		if (dto.getFacturacion().isModo()) {
			estado.setIdEstadoPresupuesto(1);
		} else {
			estado.setIdEstadoPresupuesto(2);
		}
		presupuesto.setEstadoPresupuesto(estado);
		//Mapear viajes
		presupuesto.setViajes(mapViajes(dto.getViajes(), presupuesto));
		// Mapear muestras
	    presupuesto.setMuestras(mapMuestras(dto.getItems(), presupuesto));
	}

	private UnidadNegocio mapUnidadNegocio(UnidadNegocioDto unidadNegocioDto) {
		if (unidadNegocioDto == null || unidadNegocioDto.getIdUnidadNegocio() == null) {
			throw new IllegalArgumentException("Cliente es obligatorio");
		}
		UnidadNegocio unidadNegocio = new UnidadNegocio();
		unidadNegocio.setIdUnidadNegocio(unidadNegocioDto.getIdUnidadNegocio());
		return unidadNegocio;
	}

	private Cliente mapCliente(final ClienteDto clienteDto) {
		if (clienteDto == null || clienteDto.getIdCliente() == null) {
			throw new IllegalArgumentException("Cliente es obligatorio");
		}
		Cliente cliente = new Cliente();
		cliente.setIdCliente(clienteDto.getIdCliente());
		return cliente;
	}

	private Moneda mapMoneda(final MonedaDto monedaDto) {
		Moneda moneda = new Moneda();
		if (monedaDto != null && monedaDto.getIdMoneda() != null) {
			moneda.setIdMoneda(monedaDto.getIdMoneda());
		}
		return moneda;
	}

	private Derivante mapDerivante(final DerivanteDTO derivanteDto) {
		if (derivanteDto == null || derivanteDto.getIdDerivante() == null) {
			return null;
		}
		Derivante derivante = new Derivante();
		derivante.setIdDerivante(derivanteDto.getIdDerivante());
		return derivante;
	}

	private Empleado mapEmpleado(final Integer empleadoIdStr, final String campo) {
		if (empleadoIdStr == null) {
			return null;
		}
		try {
			Empleado empleado = new Empleado();
			empleado.setIdEmpleado(Integer.valueOf(empleadoIdStr));
			return empleado;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(campo + " inválido: " + empleadoIdStr);
		}
	}

	private Contacto mapContacto(final ContactoDTO contactoDto) {
		if (contactoDto == null || contactoDto.getIdContacto() == null) {
			return null;
		}
		Contacto contacto = new Contacto();
		contacto.setIdContacto(contactoDto.getIdContacto());
		return contacto;
	}

	private Integer parseInteger(final String valor) {
		if (valor == null || valor.isEmpty()) {
			return null;
		}
		try {
			return Integer.valueOf(valor);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Valor numérico inválido: " + valor);
		}
	}

	private List<Viaje> mapViajes(List<ViajeDTO> viajesDto, Presupuesto presupuesto) {
		if (viajesDto == null || viajesDto.isEmpty()) {
			return new ArrayList<>();
		}

		return viajesDto.stream().map(viajeDto -> {
			Viaje viaje = new Viaje();
			viaje.setUbicacion(viajeDto.getUbicacion());
			viaje.setCostoViaticoPorViaje(parseInteger(viajeDto.getCostoViaticos()));
			viaje.setCantidadViajes(parseInteger(viajeDto.getCantidadViajes()));
			viaje.setTraslado(parseInteger(viajeDto.getTrasladoKm()));
			viaje.setAlojamiento(parseInteger(viajeDto.getAlojamientoDias()));
			viaje.setViaticos(parseInteger(viajeDto.getViaticosUnidades()));
			viaje.setPresupuesto(presupuesto); // Relación bidireccional
			return viaje;
		}).toList();
	}

	private List<Muestra> mapMuestras(List<ItemDTO> muestrasDto, Presupuesto presupuesto) {
	    if (muestrasDto == null || muestrasDto.isEmpty()) {
	        return new ArrayList<>();
	    }

	    return muestrasDto.stream().map(muestraDto -> {
	        Muestra muestra = new Muestra();
	        muestra.setTitulo(muestraDto.getTitulo());

	        // Relacionar referencia normativa (puede ser por ID)
	        if (muestraDto.getReferencia() != null) {
	            ReferenciaNormativa ref = new ReferenciaNormativa();
	            ref.setId(muestraDto.getReferencia());
	            muestra.setReferenciaNormativa(ref);
	        }

	        // Relacionar matriz
	        if (muestraDto.getMatriz() != null) {
	            Matriz matriz = new Matriz();
	            matriz.setId(muestraDto.getMatriz());
	            muestra.setMatriz(matriz);
	        }

	        muestra.setPe(muestraDto.getPe());
	        muestra.setCantidadVeces(muestraDto.getVeces());
	        muestra.setFrecuencia(muestraDto.getFrecuencia());
	        muestra.setCantidadMuestras(muestraDto.getMuestras());
	        muestra.setOos(muestraDto.isOos());
	        muestra.setRoos(muestraDto.isRoos());
	        muestra.setSCrudos(muestraDto.isSCrudos());

	        // Relacionar estado muestra
	       if (muestraDto != null) {
	            EstadoMuestra estado = new EstadoMuestra();
	            estado.setId(1); // se genera un id que sea en proceso para hacer pruebas hasta que se defina las reglas de negocio
	            muestra.setEstadoMuestra(estado);
	        }
	        muestra.setPresupuesto(presupuesto);

	        // 🔗 Mapear determinaciones de la muestra
	        if (muestraDto.getDeterminaciones() != null && !muestraDto.getDeterminaciones().isEmpty()) {
	            List<Determinacion> determinaciones = muestraDto.getDeterminaciones().stream()
	                    .map(detDto -> {
	                        Determinacion det = new Determinacion();
	                        det.setEspecificacion(detDto.getEspecificacion());
	                        det.setLimite(detDto.getLimite());
	                        det.setInforma(detDto.getInforma());
	                        det.setCondicionantes(detDto.getCondicionantes());
	                        det.setDtoCantidad(detDto.getDtoCantidad());
	                        det.setDtoArbitrario(detDto.getDtoArbitrario());
	                        det.setDtoCliente(detDto.getDtoCliente());
	                        det.setDtoPorcentaje(detDto.getDtoPorcentaje());
	                        det.setPrecioLista(detDto.getPrecioLista());
	                        det.setPrecioFinal(detDto.getPrecioFinal());
	                        det.setCrudos(detDto.getCrudos());
	                        det.setResultado(detDto.getResultado());
	                        det.setReferencia(detDto.getReferencia());
	                        det.setDerivado(detDto.getDatosCrudos());

	                        // Relaciones con otras tablas
	                        if (detDto.getIdDeterminacion() != null) {
	                            AbmDeterminacion determinacion = new AbmDeterminacion();
	                            determinacion.setIdDeterminacion(detDto.getIdDeterminacion());
	                            det.setDeterminacion(determinacion);
	                        }
	                        if (detDto.getIdUnidadDeterminacion() != null) {
	                            AbmUnidadDeterminacion unidad = new AbmUnidadDeterminacion();
	                            unidad.setIdUnidadDeterminacion(detDto.getIdUnidadDeterminacion());
	                            det.setUnidadDeterminacion(unidad);
	                        }
	                        if (detDto.getIdFti() != null) {
	                            AbmFti fti = new AbmFti();
	                            fti.setIdFti(detDto.getIdFti());
	                            det.setFti(fti);
	                        }
	                        if (detDto.getIdEstadoDeterminacion() != null) {
	                            AbmEstadoDeterminaciones estadoDet = new AbmEstadoDeterminaciones();
	                            estadoDet.setIdEstadoDeterminacion(detDto.getIdEstadoDeterminacion());
	                            det.setEstadoDeterminacion(estadoDet);
	                        }

	                        // Relación inversa (FK)
	                        det.setMuestra(muestra);

	                        return det;
	                    })
	                    .toList();

	            muestra.setDeterminaciones(determinaciones);
	        }
	        
	        return muestra;
	    }).toList();
	}

	@Override
    public PresupuestoDTO obtenerPresupuestoPorId(final Integer id) {
        Presupuesto presupuesto = this.presupuestoRep.findById(id).orElse(null);
        if (presupuesto == null) {
            return null;
        }

        PresupuestoDTO dto = new PresupuestoDTO();
        dto.setBpl(presupuesto.getBpl() != null && presupuesto.getBpl() == 1);
        dto.setTitulo(presupuesto.getTitulo());
        dto.setFechaPresupuesto(ProanaUtil.formatDate(presupuesto.getFechaPresupuesto()));
        dto.setValidezPresupuesto(presupuesto.getValidezDelPresupuesto() != null ? presupuesto.getValidezDelPresupuesto().toString() : null);
        dto.setFechaAceptacion(ProanaUtil.formatDate(presupuesto.getFechaAceptacion()));
        dto.setDuracionContrato(presupuesto.getDuracionDelContrato() != null ? presupuesto.getDuracionDelContrato().toString() : null);
        dto.setFechaInicio(ProanaUtil.formatDate(presupuesto.getFechaInicio()));
        dto.setOrdenCompra(presupuesto.getOrdenDeCompra());
        dto.setReferencia(presupuesto.getReferencia());
        dto.setRevision(presupuesto.getRevision() != null ? presupuesto.getRevision().toString() : null);

        //Unidad de negocio
        if (presupuesto.getUnidadNegocio() != null) {
        	UnidadNegocioDto unidadNegocioDto = new UnidadNegocioDto();
        	unidadNegocioDto.setIdUnidadNegocio(presupuesto.getUnidadNegocio().getIdUnidadNegocio());    
        	dto.setUnidadNegocio(unidadNegocioDto);
        }
        // Cliente
        if (presupuesto.getCliente() != null) {
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setIdCliente(presupuesto.getCliente().getIdCliente());
            dto.setCliente(clienteDto);
        }

        // Moneda
        if (presupuesto.getMoneda() != null) {
            MonedaDto monedaDto = new MonedaDto();
            monedaDto.setIdMoneda(presupuesto.getMoneda().getIdMoneda());
            monedaDto.setNombre(presupuesto.getMoneda().getNombre());
            dto.setMoneda(monedaDto);
        }

        // Derivante
        if (presupuesto.getDerivante() != null) {
            DerivanteDTO derivanteDto = new DerivanteDTO();
            derivanteDto.setIdDerivante(presupuesto.getDerivante().getIdDerivante());
            dto.setDerivante(derivanteDto);
        }

        // Comercial
        if (presupuesto.getComercial() != null) {
        	EmpleadoDTO empleadoDto = new EmpleadoDTO();
        	empleadoDto.setIdEmpleado(presupuesto.getComercial().getIdEmpleado());
            dto.setComercial(empleadoDto);
        }

        // Responsable contrato
        if (presupuesto.getResponsableContrato() != null) {
        	EmpleadoDTO empleadoDto = new EmpleadoDTO();
        	empleadoDto.setIdEmpleado(presupuesto.getResponsableContrato().getIdEmpleado());
            dto.setResponsableContrato(empleadoDto);
        }

        // Contacto
        if (presupuesto.getContacto() != null) {
            ContactoDTO contactoDto = new ContactoDTO();
            contactoDto.setIdContacto(presupuesto.getContacto().getIdContacto());
            contactoDto.setNombre(presupuesto.getContacto().getNombre());
            contactoDto.setEmail(presupuesto.getContacto().getEmail());
            dto.setContacto(contactoDto);
        }

        // Viajes
       /* if (presupuesto.getViajes() != null) {
            dto.setViajes(
                presupuesto.getViajes().stream().map(v -> {
                    ViajeDTO vDto = new ViajeDTO();
                    vDto.setIdViaje(v.getIdViaje());
                    vDto.setDescripcion(v.getDescripcion());
                    return vDto;
                }).collect(Collectors.toList())
            );
        }*/

        return dto;
    }


}
