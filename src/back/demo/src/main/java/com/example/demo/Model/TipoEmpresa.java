package com.example.demo.Model;

/**
 * Enumeração que define os tipos de empresa.
 * <p>
 * O {@code TipoEmpresa} especifica se uma empresa é do tipo "COMPRADORA" ou
 * "VENDEDORA".
 * </p>
 */
public enum TipoEmpresa {

    /**
     * Representa uma empresa compradora.
     */
    COMPRADORA {
        @Override
        public String toString() {
            return "COMPRADORA";
        }
    },
    /**
     * Representa uma empresa vendedora.
     */
    VENDEDORA {
        @Override
        public String toString() {
            return "VENDEDORA";
        }
    }
}