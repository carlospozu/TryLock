import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    static ReentrantLock lockBanio1 = new ReentrantLock();
    static ReentrantLock lockBanio2 = new ReentrantLock();

    static ArrayList<Jugador> listaJugadores = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 6; i++){
            listaJugadores.add(new Jugador("nombre = " + i + " "));
        }

        for (int i = 0; i < listaJugadores.size(); i++){
            listaJugadores.get(i).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


class Jugador extends Thread {

    private String nombre;
    private boolean pisEncima = false;

    Jugador(String nombre){
        this.nombre = nombre;
    }

    @Override
    public void run() {

        if (Main.lockBanio1.tryLock()) {
            System.out.println("El jugador " + nombre + "ha entrado al baño 1");
            hacerPisEnBanio();
            System.out.println("El jugador " + nombre + "ha terminado con el baño 1");
            Main.lockBanio1.unlock();
        } else {
            if (Main.lockBanio2.tryLock()) {
                System.out.println("El jugador " + nombre + "ha entrado al baño 2");
                hacerPisEnBanio();
                System.out.println("El jugador " + nombre + "ha terminado con el baño 2");
                Main.lockBanio2.unlock();
            } else {
                System.out.println("El jugador " + nombre + "no ha encontrado baño");
                hacerPisEncima();
                System.out.println("El jugador " + nombre + "se ha hecho pis encima");
            }
        }
    }

    public void hacerPisEnBanio(){
        try {
            Thread.sleep(3000);
            pisEncima = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hacerPisEncima(){
        try {
            Thread.sleep(3000);
            pisEncima = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}