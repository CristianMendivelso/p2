/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.managedbeans;


import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ServiceFacadeException;
import edu.eci.pdsw.samples.services.ServicesFacade;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author hcadavid
 */
@ManagedBean(name="beanReporteRankingPacientes")

@SessionScoped
public class ReporteRankingPacientesBean {
    
    
    
    static ServicesFacade sf= ServicesFacade.getInstance("applicationconfig.properties");
    private int num=0;
    private int year=0;
    

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    
    
    
    public List<Paciente> getSf() throws ServiceFacadeException{
        return sf.topNPacientesPorAnyo(num,year);
    }
    
    public void setSf(ServicesFacade sf){
        

    }
    
    public void execute() throws ServiceFacadeException {
        
        sf.topNPacientesPorAnyo(num,year);
        
        
    }
    
    public ReporteRankingPacientesBean(){}
    
}
