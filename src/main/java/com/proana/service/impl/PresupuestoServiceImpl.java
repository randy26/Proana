package com.proana.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.proana.dto.ClienteDto;
import com.proana.dto.CondicionFacturacionDTO;
import com.proana.dto.CondicionesPublicacionDTO;
import com.proana.dto.ContactoDTO;
import com.proana.dto.DerivanteDTO;
import com.proana.dto.DeterminacionDTO;
import com.proana.dto.EmpleadoDTO;
import com.proana.dto.ItemDTO;
import com.proana.dto.MonedaDto;
import com.proana.dto.MuestreoDTO;
import com.proana.dto.PaqueteDTO;
import com.proana.dto.PresupuestoDTO;
import com.proana.dto.PresupuestoMuestraDTO;
import com.proana.dto.PresupuestoResumenDTO;
import com.proana.dto.UnidadNegocioDto;
import com.proana.dto.ViajeDTO;
import com.proana.model.AbmDeterminacion;
import com.proana.model.AbmEstadoDeterminaciones;
import com.proana.model.AbmFti;
import com.proana.model.AbmPaquete;
import com.proana.model.AbmUnidadDeterminacion;
import com.proana.model.Cliente;
import com.proana.model.CondicionFacturacion;
import com.proana.model.CondicionesPublicacion;
import com.proana.model.Contacto;
import com.proana.model.Derivante;
import com.proana.model.Determinacion;
import com.proana.model.Empleado;
import com.proana.model.EstadoMuestra;
import com.proana.model.EstadoPresupuesto;
import com.proana.model.Matriz;
import com.proana.model.Moneda;
import com.proana.model.Muestra;
import com.proana.model.Muestreo;
import com.proana.model.Paquete;
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

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

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
     * Lista todos los presupuestos existentes.
     *
     * @return Lista de {@link PresupuestoResumenDTO}.
     */
    @Override
    public List<PresupuestoResumenDTO> listarPresupuestos() {
        try {
            List<Object[]> rows = presupuestoRep.findPresupuestosResumen();
            return rows.stream()
                    .map(row -> new PresupuestoResumenDTO(
                            TypeConverter.toInteger(row[0]),
                            TypeConverter.toStringSafe(row[1]),
                            TypeConverter.toStringSafe(row[2]),
                            TypeConverter.toDate(row[3]),
                            TypeConverter.toInteger(row[4]),
                            TypeConverter.toDate(row[5]),
                            TypeConverter.toInteger(row[6]),
                            TypeConverter.toDate(row[7]),
                            TypeConverter.toStringSafe(row[8]),
                            TypeConverter.toStringSafe(row[9]),
                            TypeConverter.toLong(row[10]),
                            TypeConverter.toBoolean(row[11])
                    ))
                    .toList();
        } catch (Exception e) {
            logger.error("Error al obtener presupuestos", e);
            throw new RuntimeException("No se pudo obtener la lista de presupuestos", e);
        }
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
		dto.setFechaPresupuesto(ProanaUtil.formatLocalDatetoString(presupuesto.getFechaPresupuesto()));
		dto.setValidezPresupuesto(
				presupuesto.getValidezDelPresupuesto() != null ? presupuesto.getValidezDelPresupuesto().toString()
						: null);
		dto.setFechaAceptacion(ProanaUtil.formatDate(presupuesto.getFechaAceptacion()));
		dto.setDuracionContrato(
				presupuesto.getDuracionDelContrato() != null ? presupuesto.getDuracionDelContrato().toString() : null);
		dto.setFechaInicio(ProanaUtil.formatDate(presupuesto.getFechaInicio()));
		dto.setOrdenCompra(presupuesto.getOrdenDeCompra());
		dto.setReferencia(presupuesto.getReferencia());
		dto.setRevision(presupuesto.getRevision() != null ? presupuesto.getRevision().toString() : null);
		dto.setMotivo(presupuesto.getMotivo());
		dto.setModo(presupuesto.getEstadoPresupuesto().getEstadoPresupuesto());
		// Unidad de negocio
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

		if (presupuesto.getCondicionesPublicacion() != null && !presupuesto.getCondicionesPublicacion().isEmpty()) {
		    List<CondicionesPublicacionDTO> publicacionDTOs = presupuesto.getCondicionesPublicacion()
		        .stream()
		        .map(cp -> CondicionesPublicacionDTO.builder()
		                .idCondicionPublicacion(cp.getIdCondicionPublicacion())
		                .idPresupuesto(cp.getPresupuesto() != null ? cp.getPresupuesto().getIdPresupuesto() : null)
		                .autorizacionComercialPreviaDT(cp.getAutorizacionComercialPreviaDT() != null ? cp.getAutorizacionComercialPreviaDT() : false)
		                .autorizacionComercial(cp.getAutorizacionComercial() != null ? cp.getAutorizacionComercial() : false)
		                .automaticamenteFirmaDT(cp.getAutomaticamenteFirmaDT() != null ? cp.getAutomaticamenteFirmaDT() : false)
		                .seInformaConReferencias(cp.getSeInformaConReferencias() != null ? cp.getSeInformaConReferencias() : false)
		                .build()
		        )
		        .collect(Collectors.toList());

		    dto.setCondicionesPublicacion(publicacionDTOs.get(0));
		}
		
		if (presupuesto.getCondicionesFacturacion() != null && !presupuesto.getCondicionesFacturacion().isEmpty()) {
		    CondicionFacturacion cf = presupuesto.getCondicionesFacturacion().get(0);

		    CondicionFacturacionDTO cfDTO = CondicionFacturacionDTO.builder()
		            .idCondicionFacturacion(cf.getIdCondicionFacturacion())
		            .autoUltimaMuestra(cf.getAutoUltimaMuestra() != null ? cf.getAutoUltimaMuestra() : false)
		            .autoIngresaronEntre(cf.getAutoIngresaronEntre() != null ? cf.getAutoIngresaronEntre() : false)
		            .fechaInicioIngreso(cf.getFechaInicioIngreso() != null ? cf.getFechaInicioIngreso().toString() : null)
		            .fechaFinIngreso(cf.getFechaFinIngreso() != null ? cf.getFechaFinIngreso().toString() : null)
		            .autoTerminadasEntre(cf.getAutoTerminadasEntre() != null ? cf.getAutoTerminadasEntre() : false)
		            .fechaInicioTerminada(cf.getFechaInicioTerminada() != null ? cf.getFechaInicioTerminada().toString() : null)
		            .fechaFinTerminada(cf.getFechaFinTerminada() != null ? cf.getFechaFinTerminada().toString() : null)
		            .manual(cf.getManual() != null && cf.getManual() ? "manual" : null)
		            .muestraAMuestra(cf.getMuestraAMuestra() != null && cf.getMuestraAMuestra() ? "muestra_a_muestra" : null)
		            .idPresupuesto(cf.getPresupuesto() != null ? cf.getPresupuesto().getIdPresupuesto() : null)
		            .build();

		    dto.setCondicionFacturacion(cfDTO);
		}

		
		if (presupuesto.getViajes() != null && !presupuesto.getViajes().isEmpty()) {
		    List<ViajeDTO> viajesDTO = presupuesto.getViajes().stream()
		        .map(v -> {
		            ViajeDTO viajeDto = new ViajeDTO();
		            viajeDto.setId(v.getIdViaje() != null ? v.getIdViaje() : 0);
		            viajeDto.setUbicacion(v.getUbicacion());
		            viajeDto.setCostoViaticos(v.getCostoViaticoPorViaje() != null ? v.getCostoViaticoPorViaje().toString() : "0");
		            viajeDto.setCantidadViajes(v.getCantidadViajes() != null ? v.getCantidadViajes().toString() : "0");
		            viajeDto.setTrasladoKm(v.getTraslado() != null ? v.getTraslado().toString() : "0");
		            viajeDto.setAlojamientoDias(v.getAlojamiento() != null ? v.getAlojamiento().toString() : "0");
		            viajeDto.setViaticosUnidades(v.getViaticos() != null ? v.getViaticos().toString() : "0");
		            return viajeDto;
		        })
		        .collect(Collectors.toList());

		    dto.setViajes(viajesDTO); // dto es tu PresupuestoDTO principal
		}

		// Muestras -> Items
		if (presupuesto.getMuestras() != null) {
			dto.setItems(presupuesto.getMuestras().stream().map(m -> {
				ItemDTO itemDto = new ItemDTO();
				itemDto.setId(m.getIdMuestra());
				itemDto.setTitulo(m.getTitulo());
				itemDto.setReferencia(m.getReferenciaNormativa() != null ? m.getReferenciaNormativa().getId() : null);
				itemDto.setMatriz(m.getMatriz() != null ? m.getMatriz().getId() : null);
				itemDto.setPe(m.getPe() != null ? m.getPe() : 0);
				itemDto.setVeces(m.getCantidadVeces() != null ? m.getCantidadVeces() : 0);
				itemDto.setFrecuencia(m.getFrecuencia() != null ? m.getFrecuencia() : 0);
				itemDto.setMuestras(m.getCantidadMuestras() != null ? m.getCantidadMuestras() : 0);
				itemDto.setOos(Boolean.TRUE.equals(m.getOos()));
				itemDto.setRoos(Boolean.TRUE.equals(m.getRoos()));
				itemDto.setSCrudos(Boolean.TRUE.equals(m.getSCrudos()));

				// 🔹 Determinaciones
				if (m.getDeterminaciones() != null) {
					itemDto.setDeterminaciones(m.getDeterminaciones().stream().map(d -> {
						DeterminacionDTO dDto = new DeterminacionDTO();
						dDto.setIdDeterminacionPresupuesto(d.getIdDeterminacionPresupuesto());
						dDto.setIdMuestra(d.getMuestra() != null ? d.getMuestra().getIdMuestra() : null);
						dDto.setIdDeterminacion(
								d.getDeterminacion() != null ? d.getDeterminacion().getIdDeterminacion() : null);
						dDto.setEspecificacion(d.getEspecificacion());
						dDto.setLimite(d.getLimite());
						dDto.setInforma(d.getInforma());
						dDto.setCondicionantes(d.getCondicionantes());
						dDto.setDtoCantidad(d.getDtoCantidad());
						dDto.setDtoArbitrario(d.getDtoArbitrario());
						dDto.setDtoCliente(d.getDtoCliente());
						dDto.setDtoPorcentaje(d.getDtoPorcentaje());
						dDto.setPrecioLista(d.getPrecioLista());
						dDto.setPrecioFinal(d.getPrecioFinal());
						dDto.setCrudos(d.getCrudos());
						dDto.setDerivado(d.getDerivado());
						dDto.setResultado(d.getResultado());
						dDto.setReferencia(d.getReferencia());
						dDto.setIdUnidadDeterminacion(d.getUnidadDeterminacion() != null
								? d.getUnidadDeterminacion().getIdUnidadDeterminacion()
								: null);
						// ⚠️ Ojo: en la entidad `datosCrudos` es String (LOB), pero en DTO es Boolean.
						// Según tu modelo parece un flag, así que interpreto que cualquier valor no
						// nulo es TRUE.
						dDto.setDatosCrudos(d.getDatosCrudos() != null);
						dDto.setIdFti(d.getFti() != null ? d.getFti().getIdFti() : null);
						dDto.setIdEstadoDeterminacion(d.getEstadoDeterminacion() != null
								? d.getEstadoDeterminacion().getIdEstadoDeterminacion()
								: null);
						return dDto;
					}).collect(Collectors.toList()));
				}

				// 🔹 Paquetes
				if (m.getPaquetes() != null) {
					itemDto.setPaquetes(m.getPaquetes().stream().map(p -> {
						PaqueteDTO pDto = new PaqueteDTO();

						// ID: si querés el id del registro de presupuesto
						pDto.setIdPaquete(p.getIdPaquetePresupuesto());

						// Si preferís el id del paquete maestro
						// pDto.setIdPaquete(p.getAbmPaquete() != null ?
						// p.getAbmPaquete().getIdPaquete() : null);

						// Descuentos
						pDto.setDtoCantidad(p.getDto_Cantidad() != null ? Math.round(p.getDto_Cantidad()) : null);
						pDto.setDtoArbitrario(p.getDto_Arbitrario() != null ? Math.round(p.getDto_Arbitrario()) : null);
						pDto.setDtoCliente(p.getDto_Cliente() != null ? Math.round(p.getDto_Cliente()) : null);
						pDto.setDtoPorcentaje(p.getDto_Porcentaje() != null ? Math.round(p.getDto_Porcentaje()) : null);

						// Precios
						pDto.setPrecioLista(p.getPrecio_Lista() != null ? p.getPrecio_Lista().doubleValue() : null);
						pDto.setPrecioFinal(p.getPrecio_Final() != null ? p.getPrecio_Final().doubleValue() : null);

						return pDto;
					}).collect(Collectors.toList()));
				}
				// 🔹 Muestreos
				if (m.getMuestreos() != null) {
					itemDto.setMuestreos(m.getMuestreos().stream().map(mu -> {
						MuestreoDTO muDto = new MuestreoDTO();
						muDto.setIdMuestreo(mu.getIdMuestreo());
						muDto.setIdMuestra(mu.getMuestra() != null ? mu.getMuestra().getIdMuestra() : null);
						muDto.setUbicacion(mu.getUbicacion());
						muDto.setFechaEstimada(mu.getFechaEstimada());
						muDto.setCantidadMinima(mu.getCantidadMinima());
						muDto.setIdUnidadDeterminacion(
								mu.getUnidad() != null ? mu.getUnidad().getIdUnidadDeterminacion() : null);
						muDto.setMuestreadores(mu.getMuestreadores());
						muDto.setTiempoTotal(mu.getTiempoTotal());
						muDto.setConsumibles(mu.getConsumibles());
						muDto.setPrecioMuestreo(mu.getPrecioMuestreo());
						return muDto;
					}).collect(Collectors.toList()));
				}

				return itemDto;
			}).collect(Collectors.toList()));
		}

		return dto;
	}

    /**
     * Guarda un nuevo presupuesto.
     *
     * @param dto DTO con los datos del presupuesto a guardar.
     */
    @Override
    public void guardarPresupuesto(final PresupuestoDTO dto) {
        try {
            Presupuesto presupuesto = mapDtoToEntity(dto, null);
            presupuestoRep.save(presupuesto);
            logger.info("Presupuesto guardado correctamente");
        } catch (Exception e) {
            logger.error("Error al guardar presupuesto", e);
            throw e;
        }
    }

    /**
     * Actualiza un presupuesto existente.
     *
     * @param id  ID del presupuesto a actualizar.
     * @param dto DTO con los datos nuevos.
     */
    @Override
    public void actualizarPresupuesto(Integer id, PresupuestoDTO dto) {
        try {
            Presupuesto presupuestoExistente = presupuestoRep.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Presupuesto con ID " + id + " no encontrado"));

            Presupuesto presupuestoActualizado = mapDtoToEntity(dto, presupuestoExistente);
            presupuestoRep.save(presupuestoActualizado);
            logger.info("Presupuesto actualizado correctamente: ID {}", id);
        } catch (EntityNotFoundException e) {
            logger.warn("Intento de actualizar presupuesto inexistente: ID {}", id);
            throw e;
        } catch (Exception e) {
            logger.error("Error al actualizar presupuesto: ID {}", id, e);
            throw new RuntimeException("No se pudo actualizar el presupuesto", e);
        }
    }

    /**
     * Mapea un DTO a entidad, ya sea para guardar o actualizar.
     *
     * @param dto       DTO del presupuesto.
     * @param presupuestoExistente entidad existente, null si es nuevo.
     * @return Entidad Presupuesto mapeada.
     */
    private Presupuesto mapDtoToEntity(PresupuestoDTO dto, Presupuesto presupuestoExistente) {
        Presupuesto presupuesto = presupuestoExistente != null ? presupuestoExistente : new Presupuesto();

        presupuesto.setUnidadNegocio(mapUnidadNegocio(dto.getUnidadNegocio()));
        presupuesto.setBpl(dto.isBpl() ? 0 : 1);
        presupuesto.setTitulo(dto.getTitulo());
        presupuesto.setFechaPresupuesto(ProanaUtil.parseLocalDate(dto.getFechaPresupuesto()));
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
        estado.setIdEstadoPresupuesto(dto.isModo() ? 1 : 2);
        presupuesto.setEstadoPresupuesto(estado);

        presupuesto.setMotivo(dto.getMotivo());
        presupuesto.setViajes(mapViajes(dto.getViajes(), presupuesto));
        presupuesto.setMuestras(mapMuestras(dto.getItems(), presupuesto));

        if (dto.getCondicionesPublicacion() != null) {
            CondicionesPublicacion cp = new CondicionesPublicacion();
            cp.setAutorizacionComercial(dto.getCondicionesPublicacion().getAutorizacionComercial());
            cp.setAutorizacionComercialPreviaDT(dto.getCondicionesPublicacion().getAutorizacionComercialPreviaDT());
            cp.setAutomaticamenteFirmaDT(dto.getCondicionesPublicacion().getAutomaticamenteFirmaDT());
            cp.setSeInformaConReferencias(dto.getCondicionesPublicacion().getSeInformaConReferencias());
            cp.setPresupuesto(presupuesto);
            presupuesto.setCondicionesPublicacion(List.of(cp));
        }

        if (dto.getCondicionFacturacion() != null) {
            CondicionFacturacion cf = new CondicionFacturacion();
            cf.setAutoUltimaMuestra(dto.getCondicionFacturacion().getAutoUltimaMuestra());
            cf.setAutoIngresaronEntre(dto.getCondicionFacturacion().getAutoIngresaronEntre());
            cf.setFechaInicioIngreso(ProanaUtil.parseDateSql(dto.getCondicionFacturacion().getFechaInicioIngreso()));
            cf.setFechaFinIngreso(ProanaUtil.parseDateSql(dto.getCondicionFacturacion().getFechaFinIngreso()));
            cf.setAutoTerminadasEntre(dto.getCondicionFacturacion().getAutoTerminadasEntre());
            cf.setFechaInicioTerminada(ProanaUtil.parseDateSql(dto.getCondicionFacturacion().getFechaInicioTerminada()));
            cf.setFechaFinTerminada(ProanaUtil.parseDateSql(dto.getCondicionFacturacion().getFechaFinTerminada()));
            cf.setManual("Manual".equalsIgnoreCase(dto.getCondicionFacturacion().getManual()));
            cf.setMuestraAMuestra(!cf.getMuestraAMuestra());
            cf.setPresupuesto(presupuesto);
            presupuesto.setCondicionesFacturacion(List.of(cf));
        }

        return presupuesto;
    }
    // ------------------ Métodos privados auxiliares ------------------

    private UnidadNegocio mapUnidadNegocio(UnidadNegocioDto dto) {
        if (dto == null || dto.getIdUnidadNegocio() == null) {
            throw new IllegalArgumentException("Unidad de negocio es obligatoria");
        }
        UnidadNegocio unidad = new UnidadNegocio();
        unidad.setIdUnidadNegocio(dto.getIdUnidadNegocio());
        return unidad;
    }

    private Cliente mapCliente(ClienteDto dto) {
        if (dto == null || dto.getIdCliente() == null) {
            throw new IllegalArgumentException("Cliente es obligatorio");
        }
        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());
        return cliente;
    }

    private Moneda mapMoneda(MonedaDto dto) {
        if (dto == null) return null;
        Moneda moneda = new Moneda();
        moneda.setIdMoneda(dto.getIdMoneda());
        return moneda;
    }

    private Derivante mapDerivante(DerivanteDTO dto) {
        if (dto == null || dto.getIdDerivante() == null) return null;
        Derivante derivante = new Derivante();
        derivante.setIdDerivante(dto.getIdDerivante());
        return derivante;
    }

    private Empleado mapEmpleado(Integer id, String campo) {
        if (id == null) return null;
        Empleado e = new Empleado();
        e.setIdEmpleado(id);
        return e;
    }

    private Contacto mapContacto(ContactoDTO dto) {
        if (dto == null || dto.getIdContacto() == null) return null;
        Contacto c = new Contacto();
        c.setIdContacto(dto.getIdContacto());
        return c;
    }

    private Integer parseInteger(String valor) {
        if (valor == null || valor.isEmpty()) return null;
        try {
            return Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor numérico inválido: " + valor);
        }
    }

    private List<Viaje> mapViajes(List<ViajeDTO> viajesDto, Presupuesto presupuesto) {
        if (viajesDto == null || viajesDto.isEmpty()) return new ArrayList<>();
        return viajesDto.stream().map(v -> {
            Viaje viaje = new Viaje();
            viaje.setUbicacion(v.getUbicacion());
            viaje.setCostoViaticoPorViaje(parseInteger(v.getCostoViaticos()));
            viaje.setCantidadViajes(parseInteger(v.getCantidadViajes()));
            viaje.setTraslado(parseInteger(v.getTrasladoKm()));
            viaje.setPresupuesto(presupuesto);
            return viaje;
        }).collect(Collectors.toList());
    }

    private List<Muestra> mapMuestras(List<ItemDTO> muestrasDto, Presupuesto presupuesto) {
		if (muestrasDto == null || muestrasDto.isEmpty()) {
			return new ArrayList<>();
		}

		return muestrasDto.stream().map(muestraDto -> {
			Muestra muestra = new Muestra();
			muestra.setTitulo(muestraDto.getTitulo());

			// Relacionar referencia normativa
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

			// Estado de la muestra
			EstadoMuestra estado = new EstadoMuestra();
			estado.setId(1); // En proceso (prueba)
			muestra.setEstadoMuestra(estado);

			muestra.setPresupuesto(presupuesto);

			// 🔗 Mapear determinaciones
			if (muestraDto.getDeterminaciones() != null && !muestraDto.getDeterminaciones().isEmpty()) {
				List<Determinacion> determinaciones = muestraDto.getDeterminaciones().stream().map(detDto -> {
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

					det.setMuestra(muestra);
					return det;
				}).toList();

				muestra.setDeterminaciones(determinaciones);
			}

			// 🔗 Mapear paquetes
			if (muestraDto.getPaquetes() != null && !muestraDto.getPaquetes().isEmpty()) {
				List<Paquete> paquetes = muestraDto.getPaquetes().stream().map(pDto -> {
					Paquete paquete = new Paquete();
					AbmPaquete abmPaquete = new AbmPaquete();
					abmPaquete.setIdPaquete(pDto.getIdPaquete());
					paquete.setAbmPaquete(abmPaquete);
					paquete.setDto_Cantidad(pDto.getDtoCantidad() != null ? pDto.getDtoCantidad().floatValue() : null);
					paquete.setDto_Arbitrario(
							pDto.getDtoArbitrario() != null ? pDto.getDtoArbitrario().floatValue() : null);
					paquete.setDto_Cliente(pDto.getDtoCliente() != null ? pDto.getDtoCliente().floatValue() : null);
					paquete.setDto_Porcentaje(
							pDto.getDtoPorcentaje() != null ? pDto.getDtoPorcentaje().floatValue() : null);
					paquete.setPrecio_Lista(pDto.getPrecioLista() != null ? pDto.getPrecioLista().floatValue() : null);
					paquete.setPrecio_Final(pDto.getPrecioFinal() != null ? pDto.getPrecioFinal().floatValue() : null);

					paquete.setMuestra(muestra);
					return paquete;
				}).toList();

				muestra.setPaquetes(paquetes);
			}

			// 🔗 Mapear muestreos
			if (muestraDto.getMuestreos() != null && !muestraDto.getMuestreos().isEmpty()) {
				List<Muestreo> muestreos = muestraDto.getMuestreos().stream().map(muestreoDto -> {
					Muestreo muestreo = new Muestreo();
					muestreo.setUbicacion(muestreoDto.getUbicacion());
					muestreo.setFechaEstimada(muestreoDto.getFechaEstimada());
					muestreo.setCantidadMinima(muestreoDto.getCantidadMinima());

					if (muestreoDto.getIdUnidadDeterminacion() != null) {
						AbmUnidadDeterminacion unidad = new AbmUnidadDeterminacion();
						unidad.setIdUnidadDeterminacion(muestreoDto.getIdUnidadDeterminacion());
						muestreo.setUnidad(unidad);
					}

					muestreo.setMuestreadores(muestreoDto.getMuestreadores());
					muestreo.setTiempoTotal(muestreoDto.getTiempoTotal());
					muestreo.setConsumibles(muestreoDto.getConsumibles());
					muestreo.setPrecioMuestreo(muestreoDto.getPrecioMuestreo());

					muestreo.setMuestra(muestra);
					return muestreo;
				}).toList();

				muestra.setMuestreos(muestreos);
			}

			return muestra;
		}).toList();
	}
    
    @Override
    public List<PresupuestoMuestraDTO> obtenerPresupuestosConClienteYMuestras() {
        try {
            logger.info("Iniciando consulta de presupuestos con cliente y muestras...");

            // Traemos entidades completas
            List<Presupuesto> presupuestos = presupuestoRep.findPresupuestosConClienteYMuestras();

            // Mapeamos a DTO
            List<PresupuestoMuestraDTO> resultados = presupuestos.stream()
                .map(p -> PresupuestoMuestraDTO.builder()
                        .titulo(p.getTitulo())
                        .idPresupuesto(p.getIdPresupuesto())
                        .cliente(p.getCliente().getRazonSocial())
                        .muestras(p.getMuestras().stream()
                                  .map(item -> new ItemDTO(item.getIdMuestra(), item.getTitulo(), item.getCantidadMuestras(),
										  item.getOos(), item.getDeterminaciones().stream()
										  .map(d -> new DeterminacionDTO(d.getIdDeterminacionPresupuesto(), d.getEspecificacion(),
												  d.getLimite(), d.getInforma(), d.getDtoCantidad() )).collect(Collectors.toList())  ))
                                  .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

            logger.info("Consulta finalizada. Se encontraron {} registros.", 
                        resultados.size());

            return resultados;

        } catch (DataAccessException ex) {
            logger.error("Error de acceso a datos al obtener presupuestos con cliente y muestras", ex);
            throw new RuntimeException("Error al acceder a la base de datos", ex);

        } catch (Exception ex) {
            logger.error("Error inesperado al obtener presupuestos con cliente y muestras", ex);
            throw new RuntimeException("Error inesperado en el servicio de presupuestos", ex);
        }
    }

}
