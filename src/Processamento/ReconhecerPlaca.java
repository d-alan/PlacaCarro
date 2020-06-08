/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processamento;

import Catalano.IO.Serialization;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BlobsFiltering;
import Catalano.Imaging.Filters.HorizontalRunLengthSmoothing;
import Catalano.Imaging.Filters.Invert;
import Catalano.Imaging.Filters.OtsuThreshold;
import Catalano.Imaging.Filters.Resize;
import Catalano.Imaging.Filters.Rotate;
import Catalano.Imaging.Shapes.IntRectangle;
import Catalano.Imaging.Tools.Blob;
import Catalano.Imaging.Tools.BlobDetection;
import Catalano.Imaging.Tools.BlobExtractor;
import Catalano.MachineLearning.Classification.IClassifier;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

public class ReconhecerPlaca {

    public ReconhecerPlaca() {

    }

    public void aplicar(FastBitmap fb) {

        FastBitmap retorno, original;
        fb.toGrayscale();

        OtsuThreshold ot = new OtsuThreshold();
        ot.applyInPlace(fb);

        Invert i = new Invert();
        i.applyInPlace(fb);

        BlobsFiltering bf = new BlobsFiltering(4000);
        bf.applyInPlace(fb);

        JOptionPane.showMessageDialog(null, fb.toIcon());

        Rotate r = new Rotate(-5);
        r.setKeepSize(true);
        r.applyInPlace(fb);

        original = new FastBitmap(fb);

        HorizontalRunLengthSmoothing h = new HorizontalRunLengthSmoothing(70, false);
        h.applyInPlace(fb);

        JOptionPane.showMessageDialog(null, fb.toIcon());

        BlobDetection blob = new BlobDetection();

        List<Blob> blobs = blob.ProcessImage(fb);

        Collections.sort(blobs, new Comparator<Blob>() {
            @Override
            public int compare(Blob o1, Blob o2) {
                return Integer.compare(o2.getArea(), o1.getArea());
            }
        });

        IClassifier classificador = (IClassifier) Serialization.Deserialize("knnMandatory.data");
        String[] v = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (int cont = 0; cont < 2; cont++) {

            IntRectangle rect = blobs.get(cont).getBoundingBox();

            FiltroCorte fc = new FiltroCorte(rect.x, rect.y, rect.width, rect.height);
            retorno = fc.Aplicar(original);

            JOptionPane.showMessageDialog(null, retorno.toIcon());

            bf.setMinArea(4000);
            bf.applyInPlace(retorno);

            List<Blob> blobs2 = blob.ProcessImage(retorno);

            for (int j = 0; j < blobs2.size(); j++) {

                BlobExtractor be = new BlobExtractor(false);
                FastBitmap a = be.Extract(retorno, blobs2.get(j));

                Resize res = new Resize(64, 64);
                res.applyInPlace(a);

                int c = classificador.Predict(a.toArrayGrayAsDouble());
                System.out.println(v[c]);

                JOptionPane.showMessageDialog(null, a.toIcon());
            }

        }

    }
}
