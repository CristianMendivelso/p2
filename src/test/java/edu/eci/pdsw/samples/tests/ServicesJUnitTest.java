/*
 * Copyright (C) 2015 hcadavid
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
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ServiceFacadeException;
import edu.eci.pdsw.samples.services.ServicesFacade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hcadavid
 */
public class ServicesJUnitTest {

    public ServicesJUnitTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void clearDB() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "anonymous", "");
        Statement stmt = conn.createStatement();
        stmt.execute("delete from CONSULTAS");
        stmt.execute("delete from PACIENTES");
        
        conn.commit();
        conn.close();
    }

    /**
     * Obtiene una conexion a la base de datos de prueba
     * @return
     * @throws SQLException 
     */
    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "anonymous", "");        
    }
    
    @Test
    public void pruebaCeroTest() throws SQLException, ServiceFacadeException {
        //Insertar datos en la base de datos de pruebas, de acuerdo con la clase
        //de equivalencia correspondiente
        Connection conn=getConnection();
        Statement stmt=conn.createStatement();  
        
        stmt.execute("INSERT INTO `PACIENTES` (`id`, `tipo_id`, `nombre`, `fecha_nacimiento`) VALUES (9876,'ti','Carmenzo','1995-07-10')");
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262218,'2001-01-01 00:00:00','Gracias',9876,'ti')"); 
        
        
        ResultSet rs=stmt.executeQuery("select count(*) from PACIENTES");
        while (rs.next()){
            System.out.println(">>>>"+rs.getInt(1));
        }
        
        
        conn.commit();
        conn.close();
	
        //Realizar la operacion de la logica y la prueba
        
        //ServicesFacade servicios=ServicesFacade.getInstance("h2-applicationconfig.properties");
        //servicios.topNPacientesPorAnyo(2, 2005);	
        //assert ...
        //Assert.fail("Pruebas no implementadas aun...");
        
    }    
    
    
    
    //Caso de equivalencia CE1: verificar que si la fecha de la consulta insertada es muy proxima al año insertado, pero no contiene el año insertado no lo tome como año
    // de frontera
    
    @Test
    public void pruebaCE1Test() throws SQLException, ServiceFacadeException{
         //Insertar datos en la base de datos de pruebas, de acuerdo con la clase
        //de equivalencia correspondiente
        Connection conn=getConnection();
        Statement stmt=conn.createStatement();  
        
        stmt.execute("INSERT INTO `PACIENTES` (`id`, `tipo_id`, `nombre`, `fecha_nacimiento`) VALUES (9876,'ti','Carmenzo','1995-07-10')");
        stmt.execute("INSERT INTO `PACIENTES` (`id`, `tipo_id`, `nombre`, `fecha_nacimiento`) VALUES (9877,'ti','Carlos','1997-07-10')");
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262218,'1999-12-31 00:00:00','Gracias',9876,'ti')"); 
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262219,'2001-01-01 00:00:00','Gracias',9876,'ti')"); 
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262220,'2001-01-01 00:00:00','Gracias',9877,'ti')"); 
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262221,'2001-01-01 00:00:00','Gracias',9876,'ti')"); 
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262222,'2001-01-01 00:00:00','Gracias',9877,'ti')"); 

        
        
        ResultSet rs=stmt.executeQuery("select P.nombre as Nombre_Paciente ,count(C.idCONSULTAS) as Cantidad_de_consultas from PACIENTES P ,CONSULTAS C where P.id = C.PACIENTES_id and P.tipo_id=C.PACIENTES_tipo_id and year(C.fecha_y_hora)=2000 ");
        
        List<String> np=new LinkedList<>();
        
       
        
        rs = stmt.executeQuery("select count(*) as Cantidad from PACIENTES P ,CONSULTAS C where P.id = C.PACIENTES_id and P.tipo_id=C.PACIENTES_tipo_id and year(C.fecha_y_hora)=2000 ");
        
        while(rs.next()){np.add(rs.getString("Cantidad"));}


        conn.commit();
        conn.close();

        //Realizar la operacion de la logica y la prueba
        
        ServicesFacade servicios=ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Paciente> lp= servicios.topNPacientesPorAnyo(2, 2000);
        
        assertEquals("En el year 2000 hay 0 consultas por tanto se deben mostrar 0 pacientes",Integer.parseInt(np.get(0)),lp.size());
        
    
    }
    
    //Caso de equivalencia CE2: verificar el orden y la relación de los datos mostrados
    //normal
    //@Test
    public void pruebaCE1Tes2() throws SQLException, ServiceFacadeException{
         //Insertar datos en la base de datos de pruebas, de acuerdo con la clase
        //de equivalencia correspondiente
        Connection conn=getConnection();
        Statement stmt=conn.createStatement();  
        
        stmt.execute("INSERT INTO `PACIENTES` (`id`, `tipo_id`, `nombre`, `fecha_nacimiento`) VALUES (9876,'ti','Carmenzo','1995-07-10')");
        stmt.execute("INSERT INTO `PACIENTES` (`id`, `tipo_id`, `nombre`, `fecha_nacimiento`) VALUES (9877,'ti','Carlos','1997-07-10')");
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262218,'2001-12-31 00:00:00','Gracias',9876,'ti')"); 
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262219,'2001-01-01 00:00:00','Gracias',9876,'ti')"); 
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262220,'2001-01-01 00:00:00','Gracias',9877,'ti')"); 
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262221,'2001-01-01 00:00:00','Gracias',9876,'ti')"); 
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262222,'2001-01-01 00:00:00','Gracias',9877,'ti')"); 

        
        
        ResultSet rs=stmt.executeQuery("select P.nombre as Nombre_Paciente from  PACIENTES P ,CONSULTAS C where P.id = C.PACIENTES_id and P.tipo_id=C.PACIENTES_tipo_id and year(C.fecha_y_hora)=2001 group by P.nombre order by P.nombre desc ");
        
        List<String> np=new LinkedList<>();
        
        
        while(rs.next()){np.add(rs.getString("nombre"));}


        conn.commit();
        conn.close();

        //Realizar la operacion de la logica y la prueba
        
        ServicesFacade servicios=ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Paciente> lp= servicios.topNPacientesPorAnyo(2, 2000);
       
        assertEquals("No está en el orden correcto",np.get(0),lp.get(0));
        assertEquals("No está en el orden correcto",np.get(1),lp.get(1));
        assertEquals("La relacion entre el nombre y cantidad de consultas no esta bien",3,lp.get(0).getConsultas().size());
        assertEquals("La relacion entre el nombre y cantidad de consultas no esta bien",2,lp.get(1).getConsultas().size());
        
    
    }

}
