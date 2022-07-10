import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import Menu.Room;

public class Runner {
    private Boolean user = false;
    Scanner scanner = new Scanner(System.in);
    Room room = new Room();
    DateFormat format = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();

    private ArrayList<Integer> _room = new ArrayList<Integer>();
    private ArrayList<String[]> _customer = new ArrayList<String[]>();

    public Runner(){
        run();
    }

    private void clear_console(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void check_customer_end_time(String redirect){
        Boolean end_time = false;
        for(int i = 0; i < _customer.size(); i++){
            if(format.format(date).equals(_customer.get(i)[2])){
                System.out.println("Customer " + _customer.get(i)[0] + " has left the room");
                _customer.remove(i);
                for(int j = 0; j < _room.size(); j++){
                    if(_room.get(j) == Integer.parseInt(_customer.get(i)[1])){
                        _room.remove(j);
                    }
                }

                end_time = true;
            }
        }

        if(end_time){
            clear_console();
            switch (redirect) {
                case "room":
                    System.out.println("\nEnter any key to go back to room list");
                    scanner.nextLine();
                    room();
                    break;
    
                case "customer":
                    System.out.println("\nEnter any key to go back to customer list");
                    scanner.nextLine();
                    customer();
                    break;
    
                case "booking":
                    System.out.println("\nEnter any key to go back to booking list");
                    scanner.nextLine();
                    booking();
                    break;
            
                default:
                    System.out.println("\nEnter any key to go back to home");
                    scanner.nextLine();
                    home();
                    break;
            }
        }
    }

    private void run(){
        if(user){
            home();
        }else{
            login();
        }
    }

    private void login(){
        clear_console();
        Console console = System.console();
        
        System.out.print("Enter your username : ");
        String username = scanner.nextLine();

        System.out.print("Enter your password : ");
        char[] passwordArray = console.readPassword();
        String password = new String(passwordArray);

        if(username.equals("admin") && password.equals("admin")){
            user = true;
            run();
        }else{
            System.out.println("Invalid username or password");
            System.out.println("Enter to continue...");
            scanner.nextLine();
            login();
        }
    }

    private void home(){
        check_customer_end_time("home");
        clear_console();
        String[] menu = {"Room", "Customer", "Booking", "Logout"};
        int choice = 0;

        for(int i = 0; i < menu.length; i++){
            System.out.println(i + 1 + ". " + menu[i]);
        }

        try{
            System.out.print("Enter your choice : ");
            choice = Integer.parseInt(scanner.nextLine());
        }catch(Exception e){}

        switch(choice){
            case 1:
                room();
                break;
            case 2:
                customer();
                break;
            case 3:
                booking();
                break;
            case 4:
                user = false;
                run();
                break;
            default:
                home();
                break;
        }
    }

    private void booking() {
        check_customer_end_time("home");
        clear_console();
        for(int i = 0; i < 12; i++){
            System.out.println("Room " + (i + 1));
            room.roomDetail(i + 1);
            System.out.println("");
        }

        System.out.println("0. Back");
        System.out.print("Enter your choice : ");
        int choice = Integer.parseInt(scanner.nextLine());

        if(choice == 0){
            home();
        }

        if(!_room.contains(choice)){
            clear_console();
            System.out.println("Room " + choice + " is available");
            System.out.print("Enter your name : ");
            String name = scanner.nextLine();

            _customer.add(new String[]{name, format.format(date), format.format(new Date(date.getTime() + (1 * 60 * 60 * 1000))), String.valueOf(choice)});
            _room.add(choice);

            System.out.println("Has been booked " + name + " for room " + choice + " from " + format.format(date) + " to " + format.format(new Date(date.getTime() + (1 * 60 * 60 * 1000))));
            System.out.println("Enter to continue...");
            scanner.nextLine();
            home();
        }else{
            System.out.println("Room " + choice + " is not available");
            System.out.println("Enter to continue...");
            scanner.nextLine();
            booking();
        }
    }

    private void customer() {
        check_customer_end_time("customer");
        clear_console();
        if(_customer.size() > 0){
            for(int i = 0; i < _customer.size(); i++){
                System.out.println((i + 1) + ". " + _customer.get(i)[0]);
            }

            System.out.println("0. Back");
            System.out.print("Enter your choice : ");
            int choice = Integer.parseInt(scanner.nextLine());

            if(choice == 0){
                home();
            }else{
                clear_console();
                System.out.println("Name : " + _customer.get(choice - 1)[0]);
                System.out.println("Room : " + _room.get(choice - 1));
                System.out.println("Check in : " + _customer.get(choice - 1)[1]);
                System.out.println("Check out : " + _customer.get(choice - 1)[2]);
                System.out.println("Enter to continue...");
                scanner.nextLine();
                customer();
            }
        clear_console();
        }else{
            System.out.println("No customer");
            System.out.println("Enter to continue...");
            scanner.nextLine();
            home();
        }
    }

    private void room() {
        check_customer_end_time("room");
        clear_console();

        room._room = _room;
        room.roomList();
        System.out.println("0. Back");
        System.out.print("Enter your choice : ");

        int choice = Integer.parseInt(scanner.nextLine());
        if(choice == 0){
            home();
        }else{
            clear_console();
            room.roomDetail(choice);
            System.out.println("Enter to continue...");
            scanner.nextLine();
            room();
        }
    }
}
