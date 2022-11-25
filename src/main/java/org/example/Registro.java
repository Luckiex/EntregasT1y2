//1. Realiza una aplicación que genere un fichero de acceso directo con registros de una determinada entidad.
//La aplicación debe permitir, al menos, generar altas y consultas.
package org.example;

import java.io.*;
import java.util.Scanner;

public class Registro
{
    //Propiedades
    private static int isbn;
    private static String nombre;
    private static String autor;
    private static int tamLibro = 2 * (50 + 1) + 2 * Integer.SIZE / 8;

    //Método main
    public static void main(String[] args)
    {
        //Inicialización de Scanner y variables
        Scanner input = new Scanner(System.in);
        int menu = 1;

        //Bucle del menú
        while (menu != 0)
        {
            System.out.println("\t MENU DE REGISTRO DE LIBROS \n 1. Dar libro de alta\n 2. Consultar libro\n 3. Muestra los libros duplicados\n 0. Salir\n");
            menu = input.nextInt();

            switch (menu)
            {
                case 1:
                    //Obtención de datos
                    System.out.print("\nIntroduce el ISBN de tu libro: ");
                    isbn = input.nextInt();
                    System.out.print("\nIntroduce el nombre de tu libro: ");
                    nombre = input.nextLine();
                    System.out.print("\nIntroduce el autor de tu libro: ");
                    autor = input.nextLine();

                    //Inicialización de un libro
                    Libro libro = new Libro(isbn,nombre,autor);

                    //Función para dar de alta el libro
                    darDeAlta(libro);
                    break;

                case 2:
                    //Obtención de datos
                    System.out.print("\nIntroduce el ISBN de tu libro: ");
                    isbn = input.nextInt();
                    Libro aux = new Libro(isbn,"","");
                    consultar(aux);
                    break;

                case 3:
                    System.out.println("Muestra duplicados");
                    leerDuplicados();
                    break;

                case 0:
                    //Salida del programa
                    System.out.println("\nEl programa se cerrará enseguida...");
                    System.exit(0);
            }
        }
    }

    //Funciones
    public static void darDeAlta(Libro libro)
    {
        try (RandomAccessFile raf = new RandomAccessFile("libros.dat", "rw"))
        {
            //Buscar una posición teniendo en cuenta el espacio que ocupa un libro
            raf.seek((isbn - 1) * tamLibro);

            if (raf.readInt() == isbn)
                duplicados();
            else
            {
                raf.writeInt(libro.getIsbn());
                raf.writeUTF(libro.getNombre());
                raf.writeUTF(libro.getAutor());
            }
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public static void consultar(Libro libro){
        try (RandomAccessFile raf = new RandomAccessFile("albumes_raf.dat", "rw"))
        {
            raf.seek((libro.getIsbn()-1) * tamLibro);
            int id = raf.readInt();
            if(id != 0)
            {
                nombre = raf.readUTF();
                autor = raf.readUTF();
            }
            System.out.println(nombre + "\n\tAutor: " + autor + "\n\tISBN: " + id);
        }
        catch (FileNotFoundException e) { throw new RuntimeException(e); }
        catch (EOFException e) { throw new RuntimeException(e); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public static void duplicados()
    {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("duplicados.dat", true)))
        {
            dos.writeInt(isbn);
            dos.writeUTF(nombre);
            dos.writeUTF(autor);
        }
        catch (FileNotFoundException e) { throw new RuntimeException(e); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public static void leerDuplicados()
    {
        try(DataInputStream dis = new DataInputStream(new FileInputStream("duplicados.dat")))
        {
            while (dis.read()>0)
            {
                isbn = dis.readInt();
                nombre = dis.readUTF();
                autor = dis.readUTF();
                System.out.println(nombre + "\n\tAutor: " + autor + "\n\tISBN: " + isbn);
            }
        }
        catch (EOFException e) { throw new RuntimeException(e); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}