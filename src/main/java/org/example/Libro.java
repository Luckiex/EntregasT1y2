package org.example;

class Libro
{
    //Propiedades
    private int isbn;
    private String nombre;
    private String autor;

    //Constructores
    public Libro()
    {
        isbn = 10;
        nombre = "Moby Dick";
        autor = "Herman Melville";
    }
    public Libro(int isbn, String nombre, String autor)
    {
        this.isbn = isbn;
        this.nombre = nombre;
        this.autor = autor;
    }

    //Setters
    public void setIsbn(int isbn)
    {
        if(isbn<0)
            System.out.println("ISBN no válido, pruebe con un número positivo");
        else this.isbn = isbn;
    }
    public void setNombre(String nombre) { this. nombre = nombre; }
    public void setAutor(String autor) { this. autor = autor; }

    //Getters
    public int getIsbn() { return isbn; }
    public String getNombre() { return nombre; }
    public String getAutor() { return autor; }

    @Override
    public String toString() { return nombre + "\n\tAutor: " + autor + "\n\tISBN: " + isbn; }
}