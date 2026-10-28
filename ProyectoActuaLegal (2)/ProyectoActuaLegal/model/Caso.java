package model;

public class Caso {
    private int id;
    private String clase;
    private String apoderadoJudicial;
    private String demandante;
    private String demandado;
    private double cuantia;
    private String despachojudicial;
    private String radicado;
    private String actuacionesSeguimiento;
    private String etapaProcesal;
    private String actuacionesARealizar;

    public Caso(){
    }

    public Caso(String clase, String apoderadojudicial, String demandante, String demandado,
        double cuantia, String despachojudicial, String radicado, String actuacionesSeguimiento,
        String etapaProcesal, String actuacionesARealizar)
         {
            
            this.clase=clase;
            this.apoderadoJudicial=apoderadojudicial;
            this.demandante=demandante;
            this.demandado=demandado;
            this.cuantia=cuantia;
            this.despachojudicial=despachojudicial;
            this.radicado=radicado;
            this.actuacionesSeguimiento=actuacionesSeguimiento;
            this.etapaProcesal=etapaProcesal;
            this.actuacionesARealizar=actuacionesARealizar;
        }
        
        //Getters y Setters 
        public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getClase() { return clase; }
    public void setClase(String clase) { this.clase = clase; }

    public String getApoderadoJudicial() { return apoderadoJudicial; }
    public void setApoderadoJudicial(String apoderadoJudicial) { this.apoderadoJudicial = apoderadoJudicial; }

    public String getDemandante() { return demandante; }
    public void setDemandante(String demandante) { this.demandante = demandante; }

    public String getDemandado() { return demandado; }
    public void setDemandado(String demandado) { this.demandado = demandado; }

    public double getCuantia() { return cuantia; }
    public void setCuantia(double cuantia) { this.cuantia = cuantia; }

    public String getDespachoJudicial() { return despachojudicial; }
    public void setDespachoJudicial(String despachoJudicial) { this.despachojudicial = despachoJudicial; }

    public String getRadicado() { return radicado; }
    public void setRadicado(String radicado) { this.radicado = radicado; }

    public String getActuacionesSeguimiento() { return actuacionesSeguimiento; }
    public void setActuacionesSeguimiento(String actuacionesSeguimiento) { this.actuacionesSeguimiento = actuacionesSeguimiento; }

    public String getEtapaProcesal() { return etapaProcesal; }
    public void setEtapaProcesal(String etapaProcesal) { this.etapaProcesal = etapaProcesal; }

    public String getActuacionesARealizar() { return actuacionesARealizar; }
    public void setActuacionesARealizar(String actuacionesARealizar) { this.actuacionesARealizar = actuacionesARealizar; }
}

    