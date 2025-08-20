package com.proana.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proana.dto.ClienteDto;
import com.proana.dto.ContactoDTO;
import com.proana.dto.DerivanteDTO;
import com.proana.dto.DeterminacionDTO;
import com.proana.dto.EmpleadoDTO;
import com.proana.dto.ItemDTO;
import com.proana.dto.MonedaDto;
import com.proana.dto.MuestreoDTO;
import com.proana.dto.PaqueteDTO;
import com.proana.dto.PresupuestoDTO;
import com.proana.dto.PresupuestoResumenDTO;
import com.proana.dto.UnidadNegocioDto;
import com.proana.dto.ViajeDTO;
import com.proana.exception.EntidadNoEncontradaException;
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
		presupuesto.setResponsableContrato(
				mapEmpleado(dto.getResponsableContrato().getIdEmpleado(), "Responsable del Contrato"));

		presupuesto.setRevision(parseInteger(dto.getRevision()));

		presupuesto.setContacto(mapContacto(dto.getContacto()));

		EstadoPresupuesto estado = new EstadoPresupuesto();
		if (dto.getFacturacion().isModo()) {
			estado.setIdEstadoPresupuesto(2);
		} else {
			estado.setIdEstadoPresupuesto(1);
		}
		presupuesto.setEstadoPresupuesto(estado);
		// Mapear viajes
		presupuesto.setViajes(mapViajes(dto.getViajes(), presupuesto));
		// Mapear muestras
		presupuesto.setMuestras(mapMuestras(dto.getItems(), presupuesto));
		
		// Mapear Condiciones de Publicación (si existe)
	    if (dto.getCondicionesPublicacion() != null) {
	        CondicionesPublicacion cp = new CondicionesPublicacion();
	        cp.setAutorizacionComercial(dto.getCondicionesPublicacion().getAutorizacionComercial());
	        cp.setAutorizacionComercialPreviaDT(dto.getCondicionesPublicacion().getAutorizacionComercialPreviaDT());
	        cp.setAutomaticamenteFirmaDT(dto.getCondicionesPublicacion().getAutomaticamenteFirmaDT());
	        cp.setSeInformaConReferencias(dto.getCondicionesPublicacion().getSeInformaConReferencias());
	        cp.setPresupuesto(presupuesto);
	        presupuesto.setCondicionesPublicacion(List.of(cp));
	    }

	    // Mapear Condición de Facturación (si existe)
	    if (dto.getCondicionFacturacion() != null) {
	        CondicionFacturacion cf = new CondicionFacturacion();
	        cf.setAutoUltimaMuestra(dto.getCondicionFacturacion().getAutoUltimaMuestra());
	        cf.setAutoIngresaronEntre(dto.getCondicionFacturacion().getAutoIngresaronEntre());
	        cf.setFechaInicioIngreso(dto.getCondicionFacturacion().getFechaInicioIngreso());
	        cf.setFechaFinIngreso(dto.getCondicionFacturacion().getFechaFinIngreso());
	        cf.setAutoTerminadasEntre(dto.getCondicionFacturacion().getAutoTerminadasEntre());
	        cf.setFechaInicioTerminada(dto.getCondicionFacturacion().getFechaInicioTerminada());
	        cf.setFechaFinTerminada(dto.getCondicionFacturacion().getFechaFinTerminada());
	        cf.setManual(dto.getCondicionFacturacion().getManual());
	        cf.setMuestraAMuestra(dto.getCondicionFacturacion().getMuestraAMuestra());
	        cf.setPresupuesto(presupuesto);
	        presupuesto.setCondicionesFacturacion(List.of(cf));
	    }
		
		presupuestoRep.save(presupuesto);
	}

	@Override
	public void actualizarPresupuesto(Integer id, PresupuestoDTO dto) {
		Presupuesto presupuesto = presupuestoRep.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Presupuesto con ID " + id + " no encontrado"));
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
		presupuesto.setResponsableContrato(
				mapEmpleado(dto.getResponsableContrato().getIdEmpleado(), "Responsable del Contrato"));

		presupuesto.setRevision(parseInteger(dto.getRevision()));

		presupuesto.setContacto(mapContacto(dto.getContacto()));

		EstadoPresupuesto estado = new EstadoPresupuesto();
		if (dto.getFacturacion().isModo()) {
			estado.setIdEstadoPresupuesto(1);
		} else {
			estado.setIdEstadoPresupuesto(2);
		}
		presupuesto.setEstadoPresupuesto(estado);
		// Mapear viajes
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
	public PresupuestoDTO obtenerPresupuestoPorId(final Integer id) {
		Presupuesto presupuesto = this.presupuestoRep.findById(id).orElse(null);
		if (presupuesto == null) {
			return null;
		}

		PresupuestoDTO dto = new PresupuestoDTO();
		dto.setBpl(presupuesto.getBpl() != null && presupuesto.getBpl() == 1);
		dto.setTitulo(presupuesto.getTitulo());
		dto.setFechaPresupuesto(ProanaUtil.formatDate(presupuesto.getFechaPresupuesto()));
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

		// Viajes
		if (presupuesto.getViajes() != null) {
			dto.setViajes(presupuesto.getViajes().stream().map(v -> {
				ViajeDTO vDto = new ViajeDTO();
				vDto.setId(v.getIdViaje());
				// vDto.setDescripcion(v.get);
				return vDto;
			}).collect(Collectors.toList()));
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

}
