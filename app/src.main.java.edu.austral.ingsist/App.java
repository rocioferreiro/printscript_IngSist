public class App {

    public static void main(String[] args) {
        final Library library = new Library();
        if(library.someLibraryMethod()){
            System.out.println("Hello World!");
        } else {
            System.out.println("Nooooooooooo!");
        }
    }
}