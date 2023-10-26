
package proyectocalcu;

import java.io.File;
import net.sourceforge.tess4j.Tesseract;


public class TessTry {
    
    public static void main(String [] args) throws Exception {
        String inputFilePath = "C:\\Users\\maxhp\\OneDrive\\Documentos\\NetBeansProjects\\Proyectocalcu\\images\\Ecuacion.jpg";        
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\maxhp\\OneDrive\\Documentos\\NetBeansProjects\\Proyectocalcu");
        String fulltext = tesseract.doOCR(new File(inputFilePath));
        
        System.out.println(fulltext);
    }
}
