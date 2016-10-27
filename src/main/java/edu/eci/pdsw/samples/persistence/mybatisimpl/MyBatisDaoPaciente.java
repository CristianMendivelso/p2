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
package edu.eci.pdsw.samples.persistence.mybatisimpl;


import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.util.Set;
import org.apache.ibatis.session.SqlSession;
import edu.eci.pdsw.samples.persistence.DAOPaciente;
import java.util.List;
import edu.eci.pdsw.samples.persistence.mybatisimpl.mappers.PacienteMapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hcadavid
 */
public class MyBatisDaoPaciente implements DAOPaciente{

    private SqlSession currentSession=null;
    
    private PacienteMapper pmap=null;

    public MyBatisDaoPaciente(SqlSession session) {        
        pmap=session.getMapper(PacienteMapper.class);
    }

    @Override
    public List<Paciente> loadTopNPatientsInAYear(int N, int year) {
     
        return pmap.getConsulta(N,year);

    }

    
    
}
