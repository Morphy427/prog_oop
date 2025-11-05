public class TestAutomata {

    public static void main(String[] args){
        int dim = 15;
        Automata working_matrix = new Automata(dim, 0.7, 0.8);
        // Automata matrix1 = new Automata(9, 0.5, 0.5);
        // Automata matrix2 = new Automata(10, 0,0);

        working_matrix.matrixDisplay();


        // System.out.println(working_matrix.propagateDisease());
    }
}