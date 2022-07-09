import java.io.Console;
import java.util.Scanner;

public class Login {
    public Boolean login = false;

    public Login(){
        System.out.flush();
        run();
    }

    private void run(){
        String _username = "admin";
        String _password = "admin";
        String username, password;
        Scanner input = new Scanner(System.in);

        Console console = System.console();

        System.out.print("Masukan username : ");
        username = input.nextLine();

        System.out.print("Enter password: ");    
        char[] passwordArray = console.readPassword();
        password = String.valueOf(passwordArray);

        // if(username == _username && password == _password){
        //     login = true;
        //     System.out.println("Berhasil login");
        // }else if(username != _username && password != _password){
        //     System.out.println("Username atau Password salah!");
        //     System.out.println("Enter to continue...");
        //     input.nextLine();
        //     run();
        // }

        if(username.toLowerCase() == "admin"){
            System.out.println("Berhasil");
        }

        System.out.println(username);
    }
}
