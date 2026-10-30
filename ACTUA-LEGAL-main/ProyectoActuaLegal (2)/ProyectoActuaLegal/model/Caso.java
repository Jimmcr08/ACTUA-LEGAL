package model;

public class Caso {
    private int id;
    private String clase;
    private String apoderadoJudicial;
    private String demandante;
    private String demandado;
    private String cuantia;
    private String despachoJudicial;
    private String radicado;
    private String actuacionesSeguimiento;
    private String etapaProcesal;
    private String actuacionesARealizar;
    private String urlDrive;

    public Caso() {
    }

    public Caso(String clase, String apoderadoJudicial, String demandante, String demandado, 
                String cuantia, String despachoJudicial, String radicado, 
                String actuacionesSeguimiento, String etapaProcesal, 
                String actuacionesARealizar, String urlDrive) {
        this.clase = clase;
        this.apoderadoJudicial = apoderadoJudicial;
        this.demandante = demandante;
        this.demandado = demandado;
        this.cuantia = cuantia;
        this.despachoJudicial = despachoJudicial;
        this.radicado = radicado;
        this.actuacionesSeguimiento = actuacionesSeguimiento;
        this.etapaProcesal = etapaProcesal;
        this.actuacionesARealizar = actuacionesARealizar;
        this.urlDrive = urlDrive;
    }

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

    public String getCuantia() { return cuantia; }
    public void setCuantia(String cuantia) { this.cuantia = cuantia; }

    public String getDespachoJudicial() { return despachoJudicial; }
    public void setDespachoJudicial(String despachoJudicial) { this.despachoJudicial = despachoJudicial; }

    public String getRadicado() { return radicado; }
    public void setRadicado(String radicado) { this.radicado = radicado; }

    public String getActuacionesSeguimiento() { return actuacionesSeguimiento; }
    public void setActuacionesSeguimiento(String actuacionesSeguimiento) { this.actuacionesSeguimiento = actuacionesSeguimiento; }

    public String getEtapaProcesal() { return etapaProcesal; }
    public void setEtapaProcesal(String etapaProcesal) { this.etapaProcesal = etapaProcesal; }

    public String getActuacionesARealizar() { return actuacionesARealizar; }
    public void setActuacionesARealizar(String actuacionesARealizar) { this.actuacionesARealizar = actuacionesARealizar; }

    public String getUrlDrive() { return urlDrive; }
    public void setUrlDrive(String urlDrive) { this.urlDrive = urlDrive; }
}