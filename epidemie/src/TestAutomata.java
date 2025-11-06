public class TestAutomata {

    public static void main(String[] args){
        Automata working_matrix = new Automata(15, 0.7, 0.5);
        Automata unworking_matrix_1 = new Automata(10, 2,0);
        Automata unworking_matrix_2 = new Automata(10, 0.5,2);
        working_matrix.contaminateForSure();
        working_matrix.matrixDisplay();

        working_matrix.propagateDisease(5);
        System.out.println("Matrix 2");
        Automata working_matrix_2 = new Automata(10, 0.7, 0.2);
        working_matrix_2.contaminateForSure();
        working_matrix_2.propagateDiseaseUntilEnd();

        System.out.println("Matrix 3");
        Automata working_matrix_3 = new Automata(10, 0.7, 0.2);
        working_matrix_3.contaminateForSure();
        working_matrix_3.matrixDisplay();
        working_matrix_3.propagateDisease2UntilEnd();

        Automata empty_matrix = new Automata(10, 0, 0);
        empty_matrix.contaminate(5, 5);
        empty_matrix.matrixDisplay();

        Automata full_vaccinate_matrix = new Automata(10, 1, 1);
        full_vaccinate_matrix.contaminate(5, 5);
        full_vaccinate_matrix.matrixDisplay();

    }
}