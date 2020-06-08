/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processamento;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BinaryClosing;
import Catalano.Imaging.Filters.BlobsFiltering;
import Catalano.Imaging.Filters.Crop;
import Catalano.Imaging.Filters.Invert;
import Catalano.Imaging.Filters.Shrink;
import Catalano.Imaging.Filters.Threshold;
import Catalano.Imaging.Shapes.IntRectangle;
import Catalano.Imaging.Tools.BlobDetection;
import java.util.List;
import Catalano.Imaging.Tools.Blob;
import javax.swing.JOptionPane;

/**
 *
 * @author aluno
 */
public class RecortaImagens {

    public RecortaImagens() {

    }

    public int Aplicar(FastBitmap fb, int cont) {
        FastBitmap retorno;
        int indice = cont;
        fb.toGrayscale();
        Threshold t = new Threshold(100);
        t.applyInPlace(fb);
        Invert i = new Invert();
        i.applyInPlace(fb);
        BlobDetection blob = new BlobDetection();
        List<Blob> blobs = blob.ProcessImage(fb);
        BlobsFiltering bf = new BlobsFiltering(100);
        bf.applyInPlace(fb);
        Shrink s = new Shrink();

        for (Blob b : blobs) {
            IntRectangle rect = b.getBoundingBox();

            FiltroCorte fc = new FiltroCorte(rect.x, rect.y, rect.width, rect.height);
            retorno = fc.Aplicar(fb);
            s.applyInPlace(retorno);
            retorno.saveAsJPG("CortesPlaca\\" + (indice + b.getId()) + ".png");
        }
        return indice + blobs.size();
    }

}
