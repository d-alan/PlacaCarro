/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processamento;

import Catalano.Imaging.FastBitmap;

public class RecortarMandatory {
    
    public void recorta(){
        
        FastBitmap fb1= new FastBitmap("0HD.png");
        FastBitmap fb2= new FastBitmap("1HD.png");
        FastBitmap fb3= new FastBitmap("2HD.png");
        
        int indice=0;
        
        RecortaImagens ri = new RecortaImagens();
        
        indice=ri.Aplicar(fb1, indice);
        indice=ri.Aplicar(fb2, indice);
        indice=ri.Aplicar(fb3, indice);
        
        System.out.println(indice+" Imagens");
        
    }
    
}
