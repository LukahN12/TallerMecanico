package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.fichero.FuenteDatosMemoria;

public enum FabricaFuenteDatos {
    MEMORIA {
        public IFuenteDatos crear() {
            return new FuenteDatosMemoria();
        }
    };

    public abstract IFuenteDatos crear();
}
