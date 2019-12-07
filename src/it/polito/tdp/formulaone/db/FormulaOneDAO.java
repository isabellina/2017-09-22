package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.formulaone.model.Pilota;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Race> getGare(Year y){
		String sql = "Select raceId,year,circuitId,name from races  where year= ? " ;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,y.getValue());
			ResultSet rs = st.executeQuery();
			List<Race> datiStagione = new LinkedList<Race>();
			while(rs.next()) {
				datiStagione.add(new Race(rs.getInt("raceId"),Year.of(rs.getInt("year")), rs.getInt("circuitId"), rs.getString("name")));
			}
			conn.close();
			return datiStagione;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean getPilotaGare(int driverId, int raceId1, int raceId2) {
		String sql = "select (count(alias.driverId) = 2) as ret from ((select driverId from results where (raceId=? or raceId=?) and (statusId=1) and driverId=?) as alias)";
		try {
			boolean ret = false;
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,raceId1);
			st.setInt(2,raceId2);
			st.setInt(3,driverId);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				ret = rs.getInt("ret") == 1;
			}
			conn.close();
			return ret;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Integer> getPilotidiquellaGara(int raceId) {
		String sql = " select driverId from results where raceId=? "; 
		try{
			List<Integer> piloti = new LinkedList<Integer>();
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,raceId);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				piloti.add(new Integer(rs.getInt("driverId")));
			}
			conn.close();
			return piloti;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public int totGiri(int raceId) {
		int ret = -1;
		String sql = "select max(lap) as laps from lapTimes where raceid=? ;" ;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,raceId);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				ret = rs.getInt("laps") ;
			}
			conn.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<Pilota> getPilotiGara(int raceId){
		System.out.println(raceId);
		String sql = "select distinct lapTimes.driverId as driverId, maxvalues.maxLap as maxLap from (select max(lap) as maxLap, driverId from lapTimes group by driverId) as maxvalues inner join lapTimes on lapTimes.driverId=maxvalues.driverId where lapTimes.raceId=?;"; 
		try{
			List<Pilota> piloti = new LinkedList<Pilota>();
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,raceId);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				System.out.println("new pilota...");
				piloti.add(new Pilota(rs.getInt("driverId"), 0, 0,rs.getInt("maxLap")));
			}
			conn.close();
			return piloti;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int tempoGiro(int raceId, int driverId, int lap) {
		int ret= -1;
		String sql = "select milliseconds from lapTimes where raceId=? and driverId=? and lap =? " ;
		try{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,raceId);
			st.setInt(2,driverId);
			st.setInt(3,lap);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				ret = rs.getInt("milliseconds");
			}
			conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}
