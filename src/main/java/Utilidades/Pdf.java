package Utilidades;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

/**
 * Clase para gestionar la creación y manipulación de documentos PDF.
 * Utiliza la biblioteca Apache PDFBox para manejar las operaciones con archivos PDF.
 * Permite agregar y eliminar páginas del documento, así como añadir contenido
 * como texto, títulos, subtítulos y tablas.
 *
 * @version 1.1
 */
public class Pdf {
    private String ruta;
    private PDDocument doc;
    private ArrayList<PDPage> paginas;
    private float margenIzquierdo = 50;
    private float margenDerecho = 50;
    private float margenSuperior = 50;
    private float margenInferior = 50;

    // Variables para posicionamiento secuencial
    private int paginaActual = 0;
    private float posicionYActual;
    private float altoPagina = 792;
    private float anchoPagina = 612;

    /**
     * Constructor de la clase Pdf.
     * Inicializa un nuevo documento PDF y prepara la ruta donde se guardará.
     *
     * @param ruta Nombre del archivo PDF (sin extensión). Se guardará en la carpeta reportes/ del proyecto
     */
    public Pdf(String ruta){
        this.ruta = "reportes" + File.separator + ruta + ".pdf";
        doc = new PDDocument();
        paginas = new ArrayList<PDPage>();
        posicionYActual = altoPagina - margenSuperior;
    }

    /**
     * Constructor de la clase Pdf.
     * Inicializa un nuevo documento PDF y prepara la ruta donde se guardará,además de las dimensiones de la página.
     * @param ruta nombre del archivo PDF (sin extensión). Se guardará en la carpeta reportes/ del proyecto
     * @param alto El alto de cada página del pdf.
     * @param ancho El ancho de cada página del pdf.
     */
    public Pdf(String ruta, float alto, float ancho){
        this.ruta = "reportes" + File.separator + ruta + ".pdf";
        doc = new PDDocument();
        paginas = new ArrayList<PDPage>();

        this.altoPagina = alto;
        this.anchoPagina = ancho;

        posicionYActual = altoPagina - margenSuperior;
    }

    /**
     * Cierra el documento PDF y libera los recursos asociados.
     * Es importante llamar a este método cuando se termine de trabajar con el documento
     * para evitar fugas de memoria.
     *
     * @throws IOException Si ocurre un error al cerrar el documento
     */
    public void cerrar() throws IOException {
        this.doc.close();
    }

    /**
     * Añade una nueva página en blanco al documento PDF.
     * La página se agrega al final del documento y también se almacena
     * en la lista interna de páginas.
     */
    public void anadirPagina(){
        PDPage page = new PDPage();
        doc.addPage(page);
        paginas.add(page);
        // Reiniciar posición Y para la nueva página
        posicionYActual = altoPagina - margenSuperior;
        paginaActual = paginas.size() - 1;

    }

    /**
     * Elimina la última página del documento PDF.
     * Remueve tanto del documento como de la lista interna de páginas.
     *
     * @throws java.util.NoSuchElementException Si no hay páginas para eliminar
     */
    public void eliminarPagina(){
        PDPage pag = paginas.removeLast();
        this.doc.removePage(pag);
    }

    /**
     * Elimina una página específica del documento PDF según su número.
     *
     * @param numero Índice de la página a eliminar (basado en 0)
     * @throws IndexOutOfBoundsException Si el número de página está fuera del rango válido
     */
    public void eliminarPagina(int numero){
        PDPage pag = paginas.remove(numero);
        this.doc.removePage(pag);
    }

    /**
     * Añade un título principal al documento en la posición especificada.
     * El título se renderiza con fuente grande (24pt) y en negrita.
     *
     * @param numeroPagina Índice de la página donde se añadirá el título (basado en 0)
     * @param texto Texto del título a mostrar
     * @param x Coordenada X donde se posicionará el título
     * @param y Coordenada Y donde se posicionará el título
     * @throws IOException Si ocurre un error al escribir en el PDF
     * @throws IndexOutOfBoundsException Si el número de página no existe
     */
    public void anadirTitulo(int numeroPagina, String texto, float x, float y) throws IOException {
        PDPage pagina = paginas.get(numeroPagina);
        PDPageContentStream contentStream = new PDPageContentStream(doc, pagina, PDPageContentStream.AppendMode.APPEND, true);

        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 24);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(texto);
        contentStream.endText();
        contentStream.close();
    }

    /**
     * Añade un subtítulo al documento en la posición especificada.
     * El subtítulo se renderiza con fuente mediana (18pt) y en negrita.
     *
     * @param numeroPagina Índice de la página donde se añadirá el subtítulo (basado en 0)
     * @param texto Texto del subtítulo a mostrar
     * @param x Coordenada X donde se posicionará el subtítulo
     * @param y Coordenada Y donde se posicionará el subtítulo
     * @throws IOException Si ocurre un error al escribir en el PDF
     * @throws IndexOutOfBoundsException Si el número de página no existe
     */
    public void anadirSubtitulo(int numeroPagina, String texto, float x, float y) throws IOException {
        PDPage pagina = paginas.get(numeroPagina);
        PDPageContentStream contentStream = new PDPageContentStream(doc, pagina, PDPageContentStream.AppendMode.APPEND, true);

        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(texto);
        contentStream.endText();
        contentStream.close();
    }

    /**
     * Añade texto simple al documento en la posición especificada.
     * El texto se renderiza con fuente normal (12pt).
     *
     * @param numeroPagina Índice de la página donde se añadirá el texto (basado en 0)
     * @param texto Texto a mostrar
     * @param x Coordenada X donde se posicionará el texto
     * @param y Coordenada Y donde se posicionará el texto
     * @throws IOException Si ocurre un error al escribir en el PDF
     * @throws IndexOutOfBoundsException Si el número de página no existe
     */
    public void anadirTexto(int numeroPagina, String texto, float x, float y) throws IOException {
        PDPage pagina = paginas.get(numeroPagina);
        PDPageContentStream contentStream = new PDPageContentStream(doc, pagina, PDPageContentStream.AppendMode.APPEND, true);

        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(texto);
        contentStream.endText();
        contentStream.close();
    }

    /**
     * Añade un párrafo de texto con ajuste automático de línea.
     * El texto se divide automáticamente cuando alcanza el ancho máximo especificado.
     *
     * @param numeroPagina Índice de la página donde se añadirá el párrafo (basado en 0)
     * @param texto Texto del párrafo a mostrar
     * @param x Coordenada X inicial del párrafo
     * @param y Coordenada Y inicial del párrafo
     * @param anchoMaximo Ancho máximo del párrafo antes de hacer salto de línea
     * @param interlineado Espaciado entre líneas
     * @throws IOException Si ocurre un error al escribir en el PDF
     * @throws IndexOutOfBoundsException Si el número de página no existe
     */
    public void anadirParrafo(int numeroPagina, String texto, float x, float y, float anchoMaximo, float interlineado) throws IOException {
        PDPage pagina = paginas.get(numeroPagina);
        PDPageContentStream contentStream = new PDPageContentStream(doc, pagina, PDPageContentStream.AppendMode.APPEND, true);

        PDType1Font fuente = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        float tamanoFuente = 12;

        contentStream.beginText();
        contentStream.setFont(fuente, tamanoFuente);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.setLeading(interlineado);
        contentStream.newLineAtOffset(x, y);

        String[] palabras = texto.split(" ");
        StringBuilder lineaActual = new StringBuilder();

        for (String palabra : palabras) {
            String lineaPrueba = lineaActual.length() == 0 ? palabra : lineaActual + " " + palabra;
            float anchoLinea = fuente.getStringWidth(lineaPrueba) / 1000 * tamanoFuente;

            if (anchoLinea > anchoMaximo) {
                contentStream.showText(lineaActual.toString());
                contentStream.newLine();
                lineaActual = new StringBuilder(palabra);
            } else {
                lineaActual = new StringBuilder(lineaPrueba);
            }
        }

        if (lineaActual.length() > 0) {
            contentStream.showText(lineaActual.toString());
        }

        contentStream.endText();
        contentStream.close();
    }

    /**
     * Añade una tabla al documento PDF con encabezados y datos.
     * La tabla se dibuja con bordes y se ajusta automáticamente según el número de columnas.
     *
     * @param numeroPagina Índice de la página donde se añadirá la tabla (basado en 0)
     * @param encabezados Array con los nombres de las columnas
     * @param datos Matriz bidimensional con los datos de la tabla (filas x columnas)
     * @param x Coordenada X inicial de la tabla
     * @param y Coordenada Y inicial de la tabla (esquina superior izquierda)
     * @param anchoTabla Ancho total de la tabla
     * @throws IOException Si ocurre un error al escribir en el PDF
     * @throws IndexOutOfBoundsException Si el número de página no existe
     * @throws IllegalArgumentException Si los datos no coinciden con el número de encabezados
     */
    public void anadirTabla(int numeroPagina, String[] encabezados, String[][] datos, float x, float y, float anchoTabla) throws IOException {
        PDPage pagina = paginas.get(numeroPagina);
        PDPageContentStream contentStream = new PDPageContentStream(doc, pagina, PDPageContentStream.AppendMode.APPEND, true);

        int numeroColumnas = encabezados.length;
        int numeroFilas = datos.length;
        float alturaFila = 20;
        float anchoColumna = anchoTabla / numeroColumnas;

        // Dibujar encabezados
        contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
        contentStream.addRect(x, y - alturaFila, anchoTabla, alturaFila);
        contentStream.fill();

        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setLineWidth(1);

        // Dibujar bordes de encabezados
        for (int i = 0; i <= numeroColumnas; i++) {
            contentStream.moveTo(x + (i * anchoColumna), y);
            contentStream.lineTo(x + (i * anchoColumna), y - alturaFila);
        }
        contentStream.moveTo(x, y);
        contentStream.lineTo(x + anchoTabla, y);
        contentStream.moveTo(x, y - alturaFila);
        contentStream.lineTo(x + anchoTabla, y - alturaFila);
        contentStream.stroke();

        // Añadir texto de encabezados
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.setNonStrokingColor(Color.BLACK);

        for (int i = 0; i < numeroColumnas; i++) {
            contentStream.newLineAtOffset(x + (i * anchoColumna) + 5, y - 15);
            contentStream.showText(encabezados[i]);
            contentStream.newLineAtOffset(-(x + (i * anchoColumna) + 5), -(y - 15));
        }
        contentStream.endText();

        // Dibujar filas de datos
        float yActual = y - alturaFila;

        for (int fila = 0; fila < numeroFilas; fila++) {
            yActual -= alturaFila;

            // Dibujar bordes de la fila
            for (int i = 0; i <= numeroColumnas; i++) {
                contentStream.moveTo(x + (i * anchoColumna), yActual + alturaFila);
                contentStream.lineTo(x + (i * anchoColumna), yActual);
            }
            contentStream.moveTo(x, yActual);
            contentStream.lineTo(x + anchoTabla, yActual);
            contentStream.stroke();

            // Añadir texto de la fila
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);

            for (int col = 0; col < numeroColumnas; col++) {
                String texto = (col < datos[fila].length) ? datos[fila][col] : "";
                contentStream.newLineAtOffset(x + (col * anchoColumna) + 5, yActual + 5);
                contentStream.showText(texto);
                contentStream.newLineAtOffset(-(x + (col * anchoColumna) + 5), -(yActual + 5));
            }
            contentStream.endText();
        }

        contentStream.close();
    }

    /**
     * Añade una línea horizontal al documento.
     * Útil para separar secciones o crear divisores visuales.
     *
     * @param numeroPagina Índice de la página donde se añadirá la línea (basado en 0)
     * @param xInicio Coordenada X inicial de la línea
     * @param yInicio Coordenada Y inicial de la línea
     * @param xFin Coordenada X final de la línea
     * @param yFin Coordenada Y final de la línea
     * @param grosor Grosor de la línea en puntos
     * @throws IOException Si ocurre un error al escribir en el PDF
     * @throws IndexOutOfBoundsException Si el número de página no existe
     */
    public void anadirLinea(int numeroPagina, float xInicio, float yInicio, float xFin, float yFin, float grosor) throws IOException {
        PDPage pagina = paginas.get(numeroPagina);
        PDPageContentStream contentStream = new PDPageContentStream(doc, pagina, PDPageContentStream.AppendMode.APPEND, true);

        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setLineWidth(grosor);
        contentStream.moveTo(xInicio, yInicio);
        contentStream.lineTo(xFin, yFin);
        contentStream.stroke();
        contentStream.close();
    }

    /**
     * Guarda el documento PDF en la ruta especificada.
     * Crea el directorio /reportes/ si no existe.
     *
     * @throws IOException Si ocurre un error al guardar el archivo
     */
    public void guardar() throws IOException {
        File archivo = new File(ruta);

        // Crear el directorio padre si no existe
        File directorioPadre = archivo.getParentFile();
        if (directorioPadre != null && !directorioPadre.exists()) {
            directorioPadre.mkdirs();
        }

        // Guardar el documento
        doc.save(archivo);
    }

    /**
     * Obtiene el número total de páginas del documento.
     *
     * @return Número de páginas en el documento
     */
    public int getNumeroPaginas() {
        return paginas.size();
    }

    /**
     * Establece los márgenes del documento.
     *
     * @param izquierdo Margen izquierdo en puntos
     * @param derecho Margen derecho en puntos
     * @param superior Margen superior en puntos
     * @param inferior Margen inferior en puntos
     */
    public void setMargenes(float izquierdo, float derecho, float superior, float inferior) {
        this.margenIzquierdo = izquierdo;
        this.margenDerecho = derecho;
        this.margenSuperior = superior;
        this.margenInferior = inferior;
    }

    /**
     * Obtiene la ruta completa del archivo PDF.
     *
     * @return Ruta del archivo PDF
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Guarda el documento PDF en una ruta específica diferente a la configurada.
     * Crea los directorios necesarios si no existen.
     *
     * @param rutaPersonalizada Ruta completa donde se guardará el archivo
     * @throws IOException Si ocurre un error al guardar el archivo
     */
    public void guardarEn(String rutaPersonalizada) throws IOException {
        File archivo = new File(rutaPersonalizada);

        // Crear el directorio padre si no existe
        File directorioPadre = archivo.getParentFile();
        if (directorioPadre != null && !directorioPadre.exists()) {
            directorioPadre.mkdirs();
        }

        // Guardar el documento
        doc.save(archivo);
    }

    /**
     * Añade un título de manera secuencial.
     * El título se añade en la posición actual del cursor y lo actualiza.
     *
     * @param texto Texto del título a mostrar
     * @throws IOException Si ocurre un error al escribir en el PDF
     */
    public void anadirTituloSecuencial(String texto) throws IOException {
        verificarEspacioYCrearPagina(30); // Verificar si hay espacio suficiente
        anadirTitulo(paginaActual, texto, margenIzquierdo, posicionYActual);
        posicionYActual -= 35; // Mover cursor hacia abajo
    }

    /**
     * Añade un subtítulo de manera secuencial.
     *
     * @param texto Texto del subtítulo a mostrar
     * @throws IOException Si ocurre un error al escribir en el PDF
     */
    public void anadirSubtituloSecuencial(String texto) throws IOException {
        verificarEspacioYCrearPagina(25);
        anadirSubtitulo(paginaActual, texto, margenIzquierdo, posicionYActual);
        posicionYActual -= 28;
    }

    /**
     * Añade texto simple de manera secuencial.
     *
     * @param texto Texto a mostrar
     * @throws IOException Si ocurre un error al escribir en el PDF
     */
    public void anadirTextoSecuencial(String texto) throws IOException {
        verificarEspacioYCrearPagina(18);
        anadirTexto(paginaActual, texto, margenIzquierdo, posicionYActual);
        posicionYActual -= 20;
    }

    /**
     * Añade un párrafo de manera secuencial con ajuste automático de línea.
     *
     * @param texto Texto del párrafo a mostrar
     * @throws IOException Si ocurre un error al escribir en el PDF
     */
    public void anadirParrafoSecuencial(String texto) throws IOException {
        float anchoMaximo = anchoPagina - margenIzquierdo - margenDerecho;
        float interlineado = 15;

        // Calcular número aproximado de líneas
        PDType1Font fuente = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        float tamanoFuente = 12;
        String[] palabras = texto.split(" ");
        int lineasAproximadas = 1;
        StringBuilder lineaActual = new StringBuilder();

        for (String palabra : palabras) {
            String lineaPrueba = lineaActual.length() == 0 ? palabra : lineaActual + " " + palabra;
            float anchoLinea = fuente.getStringWidth(lineaPrueba) / 1000 * tamanoFuente;

            if (anchoLinea > anchoMaximo) {
                lineasAproximadas++;
                lineaActual = new StringBuilder(palabra);
            } else {
                lineaActual = new StringBuilder(lineaPrueba);
            }
        }

        float espacioNecesario = lineasAproximadas * interlineado + 10;
        verificarEspacioYCrearPagina(espacioNecesario);

        anadirParrafo(paginaActual, texto, margenIzquierdo, posicionYActual, anchoMaximo, interlineado);
        posicionYActual -= espacioNecesario;
    }

    /**
     * Añade una línea horizontal de manera secuencial.
     *
     * @param grosor Grosor de la línea en puntos
     * @throws IOException Si ocurre un error al escribir en el PDF
     */
    public void anadirLineaSecuencial(float grosor) throws IOException {
        verificarEspacioYCrearPagina(10);
        float anchoLinea = anchoPagina - margenIzquierdo - margenDerecho;
        anadirLinea(paginaActual, margenIzquierdo, posicionYActual, margenIzquierdo + anchoLinea, posicionYActual, grosor);
        posicionYActual -= 15;
    }

    /**
     * Añade una tabla de manera secuencial.
     *
     * @param encabezados Array con los nombres de las columnas
     * @param datos Matriz bidimensional con los datos de la tabla
     * @throws IOException Si ocurre un error al escribir en el PDF
     */
    public void anadirTablaSecuencial(String[] encabezados, String[][] datos) throws IOException {
        float anchoTabla = anchoPagina - margenIzquierdo - margenDerecho;
        float alturaTabla = (datos.length + 1) * 20 + 10; // +1 por encabezado

        verificarEspacioYCrearPagina(alturaTabla);
        anadirTabla(paginaActual, encabezados, datos, margenIzquierdo, posicionYActual, anchoTabla);
        posicionYActual -= alturaTabla;
    }

    /**
     * Añade un salto de línea (espacio en blanco).
     *
     * @param espacio Cantidad de espacio a añadir en puntos
     */
    public void saltoDeLinea(float espacio) {
        posicionYActual -= espacio;
        if (posicionYActual < margenInferior) {
            anadirPagina();
        }
    }

    /**
     * Verifica si hay espacio suficiente en la página actual.
     * Si no hay espacio, crea una nueva página automáticamente.
     *
     * @param espacioNecesario Espacio necesario en puntos
     */
    private void verificarEspacioYCrearPagina(float espacioNecesario) {
        if (posicionYActual - espacioNecesario < margenInferior) {
            anadirPagina();
        }
    }

    /**
     * Reinicia el cursor a la parte superior de la página actual.
     */
    public void reiniciarCursor() {
        posicionYActual = altoPagina - margenSuperior;
    }

    /**
     * Obtiene la posición Y actual del cursor.
     *
     * @return Posición Y actual
     */
    public float getPosicionYActual() {
        return posicionYActual;
    }

    /**
     * Establece manualmente la posición Y del cursor.
     *
     * @param y Nueva posición Y
     */
    public void setPosicionY(float y) {
        this.posicionYActual = y;
    }
}
