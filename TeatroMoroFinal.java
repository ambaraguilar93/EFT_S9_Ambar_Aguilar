
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;

//Pruebas y depuracion
//Este sistema esta basado en clases y metodos, cada uno probado para asegurar que su rendimiento sea el deseado tanto a nivel individual como en conjunto.
//Se identificaron y corrigieron errores utilizando las herramientas de depuracion para observar paso a paso el comportamiento de las clases y metodos utilizados.
//Por ejemplo, como las clases estan enlazadas entre ellas y como algunas necesitan de la otra para funcionar correctamente. 

class Cliente {
	String nombreCliente;
	int edadCliente;
	char genero;
	int idCliente;

	static int id = 0;

	// sobrecarga de constructores
	public Cliente(String nombreCliente, int edadCliente, char genero) { // parametros
		this.nombreCliente = nombreCliente;
		this.edadCliente = edadCliente;
		this.genero = genero;
		this.idCliente = ++id;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public int getEdadCliente() {
		return edadCliente;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public char getGenero() {
		return genero;
	}

	public int getId() {
		return id;
	}

	public void guardarCliente(ArrayList<Cliente> clientes) {
		clientes.add(this);
	}

	@Override
	public String toString() {
		return "ID cliente: " + this.idCliente + ", Nombre: " + this.nombreCliente + ", Edad: " + this.edadCliente
				+ ", Genero: "
				+ this.genero;
	}

}

class Reserva {
	String asiento;
	int indiceAsiento;
	int precio;
	int cantidadEntradas;
	int idReserva;
	Cliente cliente;

	static int id = 0;

	public Reserva(String asiento, int indiceAsiento, int precio, int cantidadEntradas, Cliente cliente) {
		this.asiento = asiento;
		this.indiceAsiento = indiceAsiento;
		this.precio = precio;
		this.cantidadEntradas = cantidadEntradas;
		this.idReserva = ++id;
		this.cliente = cliente;
	}

	public String getAsiento() {
		return asiento;
	}

	public int getIndiceAsiento() {
		return indiceAsiento;
	}

	public int getPrecio() {
		return precio;
	}

	public int getCantidadEntradas() {
		return cantidadEntradas;
	}

	public int getIdReserva() {
		return idReserva;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void guardarReserva(ArrayList<Reserva> reservas) {
		reservas.add(this);
	}

	@Override
	public String toString() {
		return "ID reserva: " + this.idReserva + ", Asiento: " + this.asiento + ", Precio unitario: " + this.precio
				+ ", Cantidad entradas: "
				+ this.cantidadEntradas;
	}

}

class Venta {
	String nombreCliente;
	int edad;
	int idCliente;
	char genero;

	String asiento;
	int indiceAsiento;
	int precio;
	int cantidadEntradas;
	int idReserva;

	int idVenta;
	int totalBruto;
	int totalConDscto;

	static int id = 0;

	public Venta(Reserva reserva, Cliente cliente) {
		this.nombreCliente = cliente.getNombreCliente();
		this.edad = cliente.getEdadCliente();
		this.idCliente = cliente.getIdCliente();
		this.genero = cliente.getGenero();

		this.asiento = reserva.getAsiento();
		this.indiceAsiento = reserva.getIndiceAsiento();
		this.precio = reserva.getPrecio();
		this.cantidadEntradas = reserva.getCantidadEntradas();
		this.idReserva = reserva.getIdReserva();

		this.totalBruto = calcularTotalBruto(reserva.getPrecio(), reserva.getCantidadEntradas());
		this.totalConDscto = calcularDescuento(totalBruto,
				seleccionarDescuento(cliente.getEdadCliente(), cliente.getGenero()));

		this.idVenta = ++id;

	}

	public int getIdVenta() {
		return idVenta;
	}

	private void setCantidadEntradas(int nuevaCantEntradas) {
		this.cantidadEntradas = nuevaCantEntradas;
	}

	private void setTotalBruto(int nuevoTotalbruto) {
		this.totalBruto = nuevoTotalbruto;
	}

	private void setTotalDescuento(int nuevoTotalDescuento) {
		this.totalConDscto = nuevoTotalDescuento;
	}

	private int calcularTotalBruto(int precio, int cantEntradas) {
		return precio * cantEntradas;
	}

	public void cambiarCantidadEntradas(int nuevaCantEntradas) {
		this.setCantidadEntradas(nuevaCantEntradas);
		int nuevoTotalBruto = this.calcularTotalBruto(this.precio, nuevaCantEntradas);
		this.setTotalBruto(nuevoTotalBruto);
		int nuevoTotalDescuento = calcularDescuento(nuevoTotalBruto, seleccionarDescuento(this.edad, this.genero));
		this.setTotalDescuento(nuevoTotalDescuento);
	}

	private double seleccionarDescuento(int edad, char genero) {

		if (genero == 'M') {
			if (edad >= 65) {
				return 0.75;
			} else {
				return 0.8;
			}
		}

		if (genero == 'H') {

			if (edad >= 0 && edad <= 5) {
				return 0.90;
			} else if (edad >= 6 && edad <= 18) {
				return 0.85;
			} else if (edad >= 65) {
				return 0.75;
			}
		}

		return 1;

	}

	private int calcularDescuento(int totalBruto, double descuento) {
		return (int) (totalBruto * descuento);
	}

	public void guardarVenta(LinkedList<Venta> ventas) {
		ventas.add(this);
	}

	@Override
	public String toString() {
		return "ID Venta: "
				+ this.idVenta
				+ ", Nombre cliente: "
				+ this.nombreCliente
				+ ", Asiento: "
				+ this.asiento
				+ ", Cantidad entradas: "
				+ this.cantidadEntradas
				+ ", Total bruto: "
				+ this.totalBruto
				+ ", Total con Descuento: "
				+ this.totalConDscto;
	}

}

public class TeatroMoroFinal {
	static Scanner scanner = new Scanner(System.in);
	static String[] nombreAsientos = { "VIP", "Platea Alta", "Platea Baja", "Palco", "Galeria" };
	static int[] precioAsientos = { 30000, 18000, 15000, 13000, 10000 };
	static int[] stockAsientos = { 5, 5, 5, 5, 5 };

	static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	static ArrayList<Reserva> reservas = new ArrayList<Reserva>();

	static LinkedList<Venta> ventas = new LinkedList<Venta>();

	// Optimizacion del rendimiento.
	// El sistema de Teatro Moro esta optimizado al utilizar distintas herramientas
	// como Listas, Arreglos y Listas Enlazadas.
	// Se utilizan estructuras eficientes como LinkedList para agregar y eliminar
	// elementos, controlando a su vez el stock de las entradas
	// asegurando asi la integridad de los datos.

	public static void main(String[] args) {

		try {
			int respuestaMenu;

			System.out.println("Bienvenido al Teatro Moro.\n");

			do {

				mostrarMenu();
				respuestaMenu = scanner.nextInt();

				switch (respuestaMenu) {
					case 1:
						System.out.println("Usted elijio hacer una reserva\n");

						if (clientes.size() > 0 || reservas.size() > 0) {
							System.out.println("Ya hay una reserva en curso, proceda a comprar.\n");
							break;
						}
						Cliente cliente = identificarCliente();

						if (cliente.nombreCliente.length() < 3) {
							System.out.println("El nombre debe contener al menos 3 letras.\n");
							break;
						}

						if (cliente.edadCliente <= 0 || cliente.edadCliente > 120) {
							System.out.println("La edad debe estar en un rango de 1 a 120.\n");
							break;
						}

						if (cliente.genero != 'H' && cliente.genero != 'M') {
							System.out.println("La opcion ingresada no es valida. Debe ser M o H");
							break;
						}

						mostrarAsientos();
						System.out.println("Seleccione un asiento");
						int indiceAsiento = scanner.nextInt() - 1;

						if (indiceAsiento > nombreAsientos.length - 1 || indiceAsiento < 0) {
							System.out.println("El indice ingresado no es valido.");
							break;
						}

						System.out.println("Cuantas entradas desea reservar para el asiento " + nombreAsientos[indiceAsiento]);
						int cantidadEntradasReservadas = scanner.nextInt();

						if (cantidadEntradasReservadas > stockAsientos[indiceAsiento] || cantidadEntradasReservadas <= 0) {
							System.out.println("La cantidad de entradas ingresadas no es valida.");
							break;
						}
						cliente.guardarCliente(clientes);

						System.out.println(cliente.toString());
						Reserva reserva = crearReserva(nombreAsientos, precioAsientos, stockAsientos, cliente, indiceAsiento,
								cantidadEntradasReservadas);
						reserva.guardarReserva(reservas);
						System.out.println(reserva.toString());
						break;
					case 2:
						System.out.println("Usted elijio hacer una compra.\n");
						if (clientes.size() < 0 || reservas.size() < 0) {
							System.out.println("No hay reserva.");
							return;
						}
						Reserva datosReserva = reservas.get(0);
						Cliente datosCliente = clientes.get(0);

						Venta venta = crearVenta(datosReserva, datosCliente);
						venta.guardarVenta(ventas);
						System.out.println(venta.toString());

						reservas.clear();
						clientes.clear();
						break;
					case 3:
						System.out.println("Usted elijio modificar una compra\n");
						modificarVenta();
						break;
					case 4:
						System.out.println("Usted elijio eliminar una compra\n");
						eliminarVenta();
						break;
					case 5:
						System.out.println("Usted elijio ver boleta\n");
						mostrarBoleta();
						break;
					case 6:
						System.out.println("Gracias por visitar Teatro Moro\n");
						break;

					default:
						System.out.println("Opcion invalida. Ingrese otra opcion.\n");
						break;
				}

			} while (respuestaMenu != 6);
			scanner.close();
		} catch (InputMismatchException e) {
			System.out.println("Error: seleccione una opcion valida");
		} finally {
			System.out.println("Fin del programa.");
			scanner.close();
		}
	}

	public static void mostrarMenu() {
		System.out.println("Por favor elija una de las siguientes opciones: ");
		System.out.println("1. Hacer una reserva");
		System.out.println("2. Hacer una compra");
		System.out.println("3. Modificar una compra");
		System.out.println("4. Eliminar una compra");
		System.out.println("5. Ver boletas");
		System.out.println("6. Salir");
		System.out.print("Seleccione una opcion: \n");

	}

	public static Cliente identificarCliente() {
		System.out.println("Ingrese su nombre: ");
		limpiarBuffer();
		String nombreCliente = scanner.nextLine();

		System.out.println("Ingrese su edad: ");
		int edadCliente = scanner.nextInt();

		System.out.println("Seleccione su genero Mujer u Hombre: M o H ");
		limpiarBuffer();
		char generoCliente = scanner.next().charAt(0);
		generoCliente = Character.toUpperCase(generoCliente);
		limpiarBuffer();

		Cliente cliente = new Cliente(nombreCliente, edadCliente, generoCliente);
		return cliente;

	}

	public static void mostrarAsientos() {
		System.out.println("Lista de asientos disponibles: ");

		for (int i = 0; i < nombreAsientos.length; i++) {
			System.out.println((i + 1) + ". Asiento " + nombreAsientos[i] + " - cantidad disponible: " + stockAsientos[i]);
		}

	}

	public static Reserva crearReserva(String[] nombreAsientos, int[] precioAsientos, int[] stockAsientos,
			Cliente cliente, int indiceAsiento, int cantidadEntradasReservadas) {

		reducirStock(indiceAsiento, cantidadEntradasReservadas, stockAsientos);

		Reserva reserva = new Reserva(nombreAsientos[indiceAsiento], indiceAsiento, precioAsientos[indiceAsiento],
				cantidadEntradasReservadas, cliente);
		System.out.println("Reserva realizada exitosamente.");
		return reserva;

	}

	public static Venta crearVenta(Reserva reserva, Cliente cliente) {

		System.out.println("Venta realizada exitosamente.");
		Venta venta = new Venta(reserva, cliente);
		return venta;

	}

	public static void mostrarVentas() {

		System.out.println("Lista de ventas: ");

		if (ventas.isEmpty()) {
			System.out.println("La lista de ventas esta vacia.\n");
			return;
		}
		
		for (int i = 0; i < ventas.size(); i++) {

			System.out.println((i + 1) + ". " + ventas.get(i));

		}
		
	}
        
        public static void mostrarBoleta() {

		System.out.println("Lista de ventas: ");

		if (ventas.isEmpty()) {
			System.out.println("La lista de ventas esta vacia.\n");
			return;
		}

		System.out.println("Boleta Teatro Moro");
		System.out.println("---------------------------");
		for (int i = 0; i < ventas.size(); i++) {

			System.out.println((i + 1) + ". " + ventas.get(i));

		}
		System.out.println("---------------------------");
		System.out.println("Gracias por su visita.\n");
	}

	public static void modificarVenta() {
		mostrarVentas();

		if (ventas.isEmpty()) {
			System.out.println("No hay ventas para modicicar.");
			return;
		}

		System.out.println("Seleccione la venta que quiere modificar: ");
		int indexModificar = scanner.nextInt() - 1;

		if (indexModificar > ventas.size() - 1 || indexModificar < 0) {
			System.out.println("El indice ingresado no es valido.");
			return;
		}

		System.out.println("Ingrese la nueva cantidad de entradas: ");
		int nuevaCantEntradas = scanner.nextInt();

		Venta ventaModificada = ventas.get(indexModificar);

		int stockMaxPorTipo = stockAsientos[ventaModificada.indiceAsiento] + ventaModificada.cantidadEntradas;
		if (nuevaCantEntradas > stockMaxPorTipo || nuevaCantEntradas <= 0) {
			System.out.println("La cantidad ingresada no es valida.");
			return;
		}

		stockAsientos[ventaModificada.indiceAsiento] = stockAsientos[ventaModificada.indiceAsiento]
				+ ventaModificada.cantidadEntradas;
		ventaModificada.cambiarCantidadEntradas(nuevaCantEntradas);

		stockAsientos[ventaModificada.indiceAsiento] = stockAsientos[ventaModificada.indiceAsiento] - nuevaCantEntradas;

		System.out.println("Venta modificada exitosamente.");

	}

	public static void eliminarVenta() {
		mostrarVentas();
		if (ventas.isEmpty()) {
			System.out.println("No hay ventas para eliminar.");
			return;
		}
		System.out.println("Seleccione la venta que quiere eliminar: ");
		int indexEliminar = scanner.nextInt() - 1;

		if (indexEliminar > ventas.size() - 1 || indexEliminar < 0) {
			System.out.println("El indice ingresado no es valido.");
			return;
		}

		Venta venta = ventas.get(indexEliminar);

		stockAsientos[venta.indiceAsiento] = stockAsientos[venta.indiceAsiento] + venta.cantidadEntradas;

		ventas.remove(indexEliminar);

		System.out.println("Venta eliminada exitosamente.");
	}

	public static void limpiarBuffer() {
		scanner.nextLine();
	}

	public static void reducirStock(int indiceAsiento, int cantidadEntradas, int[] stockAsientos) {
		stockAsientos[indiceAsiento] = stockAsientos[indiceAsiento] - cantidadEntradas;
	}

}
