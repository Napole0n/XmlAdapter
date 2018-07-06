/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xmladapter.classes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Murilo_2
 */
public class ClassDigestor{

    private Class classe;

    public ClassDigestor(Class c) {
        this.classe = c;
    }

    public ClassDigestor() {
    }

    /**
     *
     * @return a lista Fields da classe
     */
    public List<Field> getFields() {
        return Arrays.asList(classe.getDeclaredFields());
    }

    /**
     *
     * @return a lista de "Getters de atributos" da classe
     */
    public List<Method> getParemeterGetters() {
        List<Field> lField = getFields();
        List<Method> lGetters = getGetters();
        List<Method> returnMethods = new ArrayList<>();

        for (Field f : lField) {

            for (Method getter : lGetters) {
                String paramName = f.getName().toLowerCase();
                String ActualGetterName = getter.getName().replace("get", "").toLowerCase();
                if (paramName.equals(ActualGetterName)) {
                    returnMethods.add(getter);
                    break;
                }

            }

         }
        if (returnMethods.isEmpty()) {
            System.err.println("Não foram encontrados métodos publicos para acessar os parametros do objeto!");
        }
        return returnMethods;

    }
    
    /**
     *
     * @return a lista de "Setters de atributos da classe
     */
    public List<Method> getParemeterSetters() {
        List<Field> lField = getFields();
        List<Method> lSetters = getSetters();
        List<Method> returnMethods = new ArrayList<>();

        for (Field f : lField) {

            for (Method setter : lSetters) {
                String paramName = f.getName().toLowerCase();
                String actualGetterName = setter.getName().replace("set", "").toLowerCase();
                if (paramName.equals(actualGetterName)) {
                    returnMethods.add(setter);
                    break;
                }

            }

         }
        if (returnMethods.isEmpty()) {
            System.err.println("Não foram encontrados métodos publicos para gravar os parametros do objeto!");
        }
        return returnMethods;

    }

    /**
     *
     * @return a lista com todos os "Getters"
     */
    public List<Method> getGetters() {
        List<Method> getters = new ArrayList<>();
        if (classe == null) {
            System.err.println("Classe inexistente no Digestor");
        } else {

            if (classe.getMethods().length == 0) {
                System.out.println("Classe carregada não contem metodos!");
            } else {

                for (Method m : classe.getMethods()) {
                    if (m.getName().startsWith("get") && !(m.getName().equals("getClass"))) {
                        getters.add(m);
                    }
                }
            }

        }
        return getters;
    }

    /**
     *
     * @return a lista com todos os Setters
     */
    public List<Method> getSetters() {
        List<Method> getters = new ArrayList<>();
        if (classe == null) {
            System.err.println("Classe inexistente no Digestor");
        } else {

            if (classe.getMethods().length == 0) {
                System.out.println("Classe carregada não contem metodos!");
            } else {

                for (Method m : classe.getMethods()) {
                    if (m.getName().startsWith("set")) {
                        getters.add(m);
                    }
                }
            }

        }
        return getters;
    }

    /**
     *
     * @return todos os Metodos
     */
    public List<Method> getAllMethods() {
        List<Method> getters = new ArrayList<>();
        if (classe == null) {
            System.err.println("Classe inexistente no Digestor");
        } else {
            if (classe.getMethods().length == 0) {
                System.out.println("Classe carregada não contem metodos!");
            } else {

                for (Method m : classe.getMethods()) {
                    getters.add(m);
                }
            }

        }
        return getters;
    }

    /**
     *
     * @return o nome Canonico da Classe
     */
    public String getClassName() {
        return this.classe.getName();
    }

    /**
     *
     * @return o nome simples da Classe
     */
    public String getSimpleName() {
        return this.classe.getSimpleName();
    }

    /**
     *
     * @return o contatodr de "Getters" da classe
     */
    public int getGettersCount() {
        return this.getGetters().size();
    }

    /**
     *
     * @return a classe propriamente dita do objeto contido no Digestor
     */
    public Class getObjectClass() {
        return this.classe;
    }

    public void setClass(Class c) {
        this.classe = c;
    }

}
