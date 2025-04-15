package proyectobatallanaval;

import javafx.scene.media.AudioClip;

public class ReproductorSonido {
    public static void reproducir(String nombreSonido) {
        try {
            String path = ReproductorSonido.class.getResource("/Sonidos/" + nombreSonido).toString();
            AudioClip clip = new AudioClip(path);
            clip.play();
        } catch (Exception e) {
            System.out.println("Error al reproducir sonido: " + e.getMessage());
        }
    }
}
