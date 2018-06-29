/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xmladapter.classes;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Murilo_2
 */
public class XMLManip {

    private String DESTINATION_DIR = "";

    /**
     *
     * @param destination o destino do arquivo Xml
     */
    public XMLManip(String destination) {
        DESTINATION_DIR = destination;
    }

    /**
     *
     * @param <T> o Tipo do objeto que esta sendo gravado
     * @param lista a lista de objetos que estao sendo gravados
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public <T> void write(List<T> lista) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        ClassDigestor<T> cDig = new ClassDigestor<>(lista.get(0).getClass());
        List<Method> getters = cDig.getParemeterGetters();

        Element config = new Element(cDig.getSimpleName());
        Document documento = new Document(config);
        Element titulo = new Element("Title");
        titulo.setText("Persistência da classe '" + cDig.getSimpleName() + "' por Murilo Goedert");
        config.addContent(titulo);

        for (int x = 0; x < lista.size(); x++) {

            T obj = lista.get(x);

            Element element = new Element(cDig.getSimpleName().toLowerCase());

            for (Method get : getters) {
                String attributeName = get.getName().replaceAll("get", "").toLowerCase();
                Element e = new Element(attributeName);
                e.setText(String.valueOf(get.invoke(obj)));
                element.addContent(e);
            }

            config.addContent(element);
        }

        XMLOutputter xout = new XMLOutputter();
        try {
   
            BufferedWriter arquivo = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(DESTINATION_DIR), "UTF-8"));
           
            xout.output(documento, arquivo);
        } catch (IOException e) {
        }
    }
/*                                                    Não esta pronto ainda
    public <T> List<T> read(Class c) {

        List<T> listToReturn = new ArrayList<>();
        ClassDigestor<T> cDig = new ClassDigestor(c);
        List<Method> setterList = cDig.getParemeterSetters();

        Document doc = null;
        SAXBuilder builder = new SAXBuilder();

        try {
            doc = builder.build(DESTINATION_DIR);
            Element config = doc.getRootElement();
            List lista = config.getChildren(cDig.getAbsoluteName().toLowerCase());

            for (Iterator iter = lista.iterator(); iter.hasNext();) {
                Element element = (Element) iter.next();
                Class<?> instancia = Class.forName(cDig.getClassName());

                for (Method setter : setterList) {
                 
                    Class<?> classParam = Class.forName(cDig.getObjectClass().getField(setter.getName().replace("set","").toLowerCase().trim()).getType().getName());
                    setter.invoke(instancia, (classParam.cast(element.getChildText(setter.getName().replace("set", "").toLowerCase()))));

                }
                listToReturn.add((T) instancia);
            }

        } catch (Exception ex) {
            Logger.getLogger(XMLManip.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listToReturn;
    }
*/

}
