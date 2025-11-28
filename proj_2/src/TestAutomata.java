public class TestAutomata {

    public static void main(String[] args){

        System.out.println("Matrix 1");
        Automata working_matrix = new Automata(15, 0.7, 0.5, 0.1, 1);
        //Automata unworking_matrix_1 = new Automata(10, 2,0);
        //Automata unworking_matrix_2 = new Automata(10, 0.5,2);
        working_matrix.contaminateForSure();
        working_matrix.matrixDisplay();
        working_matrix.propagateDisease(5);

        System.out.println("Matrix 2");
        Automata working_matrix_2 = new Automata(10, 0.7, 0.2, 0.1, 1);
        working_matrix_2.contaminateForSure();
        working_matrix_2.propagateDiseaseUntilEnd();

        System.out.println("Matrix 3");
        Automata working_matrix_3 = new Automata(10, 0.7, 0.2, 0.1, 1);
        working_matrix_3.contaminateForSure();
        working_matrix_3.matrixDisplay();
        working_matrix_3.propagateDiseaseUntilEnd2();

        System.out.println("Matrix 4");
        Automata working_matrix_4 = new Automata(10, 0.7, 0.2, 0.9, 1);
        working_matrix_4.contaminateForSure();
        working_matrix_4.matrixDisplay();
        working_matrix_4.propagateDiseaseUntilEnd2();

        System.out.println("Matrix 5");
        Automata working_matrix_5 = new Automata(10, 0.7, 0.2, 0.1, 0.3);
        working_matrix_5.contaminateForSure();
        working_matrix_5.matrixDisplay();
        working_matrix_5.propagateDiseaseUntilEnd2();

        System.out.println("Matrix Empty");
        Automata empty_matrix = new Automata(10, 0, 0, 0.1, 1);
        empty_matrix.contaminate(5, 5);
        empty_matrix.matrixDisplay();

        System.out.println("Matrix Vaccinate only");
        Automata full_vaccinate_matrix = new Automata(10, 1, 1, 0.1, 1);
        full_vaccinate_matrix.contaminate(5, 5);
        full_vaccinate_matrix.matrixDisplay();
    }
}