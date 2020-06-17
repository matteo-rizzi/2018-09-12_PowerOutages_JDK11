package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutagesDAO {
	
	public void loadAllNercs(Map<Integer, Nerc> idMap) {

		String sql = "SELECT id, value FROM nerc";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(!idMap.containsKey(res.getInt("id"))) {
					Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
					idMap.put(res.getInt("id"), n);
				}
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer, Nerc> idMap) {
		String sql = "SELECT nerc_one AS primo, nerc_two AS secondo, COUNT(DISTINCT MONTH(po1.date_event_began), YEAR(po1.date_event_began)) AS peso FROM nercrelations AS nr , poweroutages AS po1, poweroutages AS po2 WHERE nr.nerc_one=po1.nerc_id AND nr.nerc_two = po2.nerc_id AND MONTH(po1.date_event_began) = MONTH(po2.date_event_began) AND YEAR(po1.date_event_began) = YEAR(po2.date_event_began) GROUP BY nerc_one, nerc_two";
	
		List<Adiacenza> list = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Adiacenza a = new Adiacenza(idMap.get(res.getInt("primo")), idMap.get(res.getInt("secondo")), res.getInt("peso"));
				list.add(a);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return list;
	}
}
