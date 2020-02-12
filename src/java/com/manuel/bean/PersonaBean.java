
package com.manuel.bean;
//faltan cosas
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import com.manuel.model.Persona;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

@Named(value = "personaBean")
@RequestScoped
public class PersonaBean {
    
  private List<Persona> personas =  new ArrayList<Persona>();  

    public List<Persona> getPersonas() {
        
        Persona per = new Persona();
        per.setNombres("manuel");
        per.setApellidos("cruz");
        Calendar cal = Calendar.getInstance();
        cal.set(1991,1,21);
        per.setFechaNacimiento(cal.getTime());
        personas.add(per);
        
        per = new Persona();
        per.setNombres("sebastian");
        per.setApellidos("michells");
        cal = Calendar.getInstance();
        cal.set(1990,5,21);
        per.setFechaNacimiento(cal.getTime());
        personas.add(per);
        
        
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }
public void exportarPDF(ActionEvent actionEvent) throws JRException, IOException{
    Map<String,Object> parametros = new HashMap<String,Object>(); 
    parametros.put("txtUsuario", "manuel");

File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/rptJSF.jasper"));
JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),parametros, new JRBeanCollectionDataSource(this.getPersonas()));

HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
response.addHeader("Context-disposition","attachment; filename=jsfReporte.pdf");
ServletOutputStream stream = response.getOutputStream();

JasperExportManager.exportReportToPdfStream(jasperPrint,stream);


stream.flush();
stream.close();
FacesContext.getCurrentInstance().responseComplete();
 } 

  public void exportarExcel(ActionEvent actionEvent) throws JRException, IOException{
    Map<String,Object> parametros = new HashMap<String,Object>(); 
    parametros.put("txtUsuario", "manuel");

File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/rptJSF.jasper"));
JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),parametros, new JRBeanCollectionDataSource(this.getPersonas()));

HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
response.addHeader("Context-disposition","attachment; filename=jsfReporte.xls");
ServletOutputStream stream = response.getOutputStream();


JRXLsExporter exporter = new JRXLsExporter();
exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,stream);
exporter.exportReport();

stream.flush();
stream.close();
FacesContext.getCurrentInstance().responseComplete();
  }
    public void exportarPTT(ActionEvent actionEvent) throws JRException, IOException{
    Map<String,Object> parametros = new HashMap<String,Object>(); 
    parametros.put("txtUsuario", "manuel");

File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/rptJSF.jasper"));
JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),parametros, new JRBeanCollectionDataSource(this.getPersonas()));

HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
response.addHeader("Context-disposition","attachment; filename=jsfReporte.ptt");
ServletOutputStream stream = response.getOutputStream();

//@Deprecated hace algo
JRPptExporter exporter = new JRPptExporter();

exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,stream);
exporter.exportReport();

stream.flush();
stream.close();
FacesContext.getCurrentInstance().responseComplete();
  }
      public void exportarDOC(ActionEvent actionEvent) throws JRException, IOException{
    Map<String,Object> parametros = new HashMap<String,Object>(); 
    parametros.put("txtUsuario", "manuel");

File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/rptJSF.jasper"));
JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),parametros, new JRBeanCollectionDataSource(this.getPersonas()));

HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
response.addHeader("Context-disposition","attachment; filename=jsfReporte.doc");
ServletOutputStream stream = response.getOutputStream();

//@Deprecated hace algo
JRDocxExporter exporter = new JRDocxExporter();

exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,stream);
exporter.exportReport();

stream.flush();
stream.close();
FacesContext.getCurrentInstance().responseComplete();
  }//fin del metodo exportar en doc
      
      public void verPDF(ActionEvent actionEvent) throws Exception{
      File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/rpJSF.jasper"));
      
      byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(),null,JRBeanCollectionDataSource(this.getPersonas()));
      HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
      response.setContentType("application/pdf");
      response.setContentLength(bytes.length);
      ServletOutputStream outStream = response.getOutputStream();
      outStream.write(bytes,0,bytes.length);
      outStream.flush();
      outStream.close();
      
      FacesContext.getCurrentInstance().responseComplete();
      }//fin del metodo ver pdf

    
      
}//fin de la clase
