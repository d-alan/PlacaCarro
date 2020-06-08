/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processamento;

import Catalano.IO.Serialization;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Resize;
import Catalano.MachineLearning.Classification.IClassifier;
import Catalano.MachineLearning.Classification.KNearestNeighbors;
import Catalano.MachineLearning.Dataset.DatasetClassification;
import Catalano.MachineLearning.Performance.LeaveOneOutCrossValidation;


public class Treinamento {
    
    public void Treinamento(){
        
    }
    
    public void aplicar(){
        FastBitmap fb;
        Resize r = new Resize(64, 64);
        
        int id=0;
        double[][] m = new double[510][4096];
        int[] c= new int[510];
        
        for(int i=0;i<34;i++){
            for(int j=0;j<15;j++){
                c[id++]=i;
            }
        }
        
        for(int i=0; i<510; i++){
            fb= new FastBitmap("Cortes\\"+i+".png");
            r.applyInPlace(fb);
            m[i]=fb.toArrayGrayAsDouble();//Features(fb);
        }
        
        DatasetClassification dc = new DatasetClassification("nome", m, c);
        
        IClassifier classifier = new KNearestNeighbors();
        classifier.Learn(dc);
        
        // serializar o dataset para comparar depois;
        Serialization.Serialize(classifier,"knnMandatory.data");
        
        //fazer a descerealização pra poder comparar
        IClassifier class2 = (IClassifier)Serialization.Deserialize("knnMandatory.data");
        
        //compara o elemento do vetor com o dataset
        int comp= class2.Predict(m[15]);
        
        LeaveOneOutCrossValidation val = new LeaveOneOutCrossValidation();
        
        double p = val.Run(classifier, dc);
        System.out.println(p);
    }
    
}
