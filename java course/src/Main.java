import java.util.Scanner;

public class Main {

    public static void main (String [] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("1 : To create a partner\n");
        System.out.print("2 : To modify a partner\n");
        String answer = scanner.nextLine();

        if(answer == "1"){
            System.out.println("You will create it");
        }else{
            System.out.print("You will modify it");
        }
    }
}