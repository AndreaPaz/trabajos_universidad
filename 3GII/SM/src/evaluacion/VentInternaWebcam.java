package evaluacion;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

/**
 * Ventana interna de captura de video desde webcam.
 *
 * @author German Martinez Maldonado
 */
public class VentInternaWebcam extends javax.swing.JInternalFrame {

    private Player player;

    /**
     * Obtiene una instancia de capturador de video de webcam.
     *
     * @return Si se puede capturar video desde la webcam en el sistema se
     * devuelve un reproductor que reproduzca el video que se capture, en caso
     * contrario se devolverá "null".
     */
    public static VentInternaWebcam getInstance() {
        VentInternaWebcam ventana = new VentInternaWebcam();

        if (ventana.player == null) {
            return null;
        } else {
            return ventana;
        }
    }

    /**
     * Constructor de la ventana interna de captura de video desde webcam.
     *
     */
    private VentInternaWebcam() {
        initComponents();

        play();
    }

    /**
     * Crea el reproductor del video capturado (si es posible) e inicia la
     * reproducción.
     *
     */
    public void play() {
        try {
            CaptureDeviceInfo deviceInfo;

            String dName = "vfw:Microsoft WDM Image Capture (Win32):0";
            deviceInfo = CaptureDeviceManager.getDevice(dName);

            MediaLocator ml = deviceInfo.getLocator();
            player = Manager.createRealizedPlayer(ml);

            Component areaVisual = player.getVisualComponent();
            if (areaVisual != null) {
                add(areaVisual);
            }

            player.start();
        } catch (IOException ioe) {
            player = null;
            System.err.println("Error: Fallo de entrada/salida relacionado con la webcam. " + ioe.getMessage());
        } catch (NoPlayerException npe) {
            player = null;
            System.err.println("Error: No se ha podido encontrar un reproductor para la señal de la webcam. " + npe.getMessage());
        } catch (CannotRealizeException cre) {
            player = null;
            System.err.println("Error: Fallo cuando se intentaba realizar el reproductor para la señal de la webcam. " + cre.getMessage());
        }
    }

    /**
     * Captura la imagen actual en el video que se está capturando.
     *
     * @return Imagen capturada.
     */
    public BufferedImage getFrame() {
        FrameGrabbingControl fgc;
        String claseCtr = "javax.media.control.FrameGrabbingControl";
        fgc = (FrameGrabbingControl) player.getControl(claseCtr);
        Buffer bufferFrame = fgc.grabFrame();
        BufferToImage bti;
        bti = new BufferToImage((VideoFormat) bufferFrame.getFormat());
        Image img = bti.createImage(bufferFrame);
        return (BufferedImage) img;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setClosable(true);
        setIconifiable(true);
        setTitle("Webcam");
        setMaximumSize(new java.awt.Dimension(640, 480));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 480));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                VentanaInternaWebcamClosing(evt);
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Acción correspondiente a cerrar la ventana de reproducción. Cierra el
     * reproductor del video capturado de la webcam.
     *
     * @param evt Evento que produce el inicio de la acción (cierre de ventana
     * interna).
     */
    private void VentanaInternaWebcamClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_VentanaInternaWebcamClosing
        player.close();
    }//GEN-LAST:event_VentanaInternaWebcamClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
