package com.example.demo.Repositories;

import com.example.demo.dtos.EpDevice;
import com.example.demo.dtos.EpRequestDto;
import com.example.demo.dtos.EpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class EpRepository {

    @Autowired
    @Qualifier("cspdbJdbcTemplate")
    private JdbcTemplate cspJdbcTemplate;

    //CallableStatement example: simple
    public EpResponseDto getEpDevices(EpRequestDto epReqDto) throws Exception {

        final String procedureCall = "{call GetAvailableEPs(?, ?, ?, ?, ?, ?, ?)}";
        Connection connection = null;
        EpResponseDto response = new EpResponseDto();

        try {
            connection = cspJdbcTemplate.getDataSource().getConnection();
            CallableStatement callableSt = connection.prepareCall(procedureCall);
            callableSt.setLong("p_userAccountId", epReqDto.getAccountId());
            callableSt.setLong("p_supportAccountId", epReqDto.getSupportAccountId());
            callableSt.setLong("p_PaginationOffset", epReqDto.getPageOffset());
            callableSt.setInt("p_PaginationCount", epReqDto.getPageCount());
            callableSt.setString("p_Searchfield", epReqDto.getSearchField());
            callableSt.setString("p_SearchValue", epReqDto.getSearchValue());
            callableSt.registerOutParameter("p_TotalRecordCount", Types.BIGINT);

            ResultSet rs = callableSt.executeQuery();
            handleResultSet(rs, response);
            response.setTotalCount(callableSt.getLong("p_TotalRecordCount"));
        } catch (EmptyResultDataAccessException e) {

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
            }
        }

        return response;
    }

    //CallableStatement 2: more complicated
    public EpResponseDto getEpDevices1(EpRequestDto epReqDto) {
        final String procedureCall = "{call GetAvailableEPs(?, ?, ?, ?, ?, ?, ?)}";
        EpResponseDto response = new EpResponseDto();
        List<SqlParameter> paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter(Types.BIGINT));
        paramList.add(new SqlParameter(Types.BIGINT));
        paramList.add(new SqlParameter(Types.BIGINT));
        paramList.add(new SqlParameter(Types.INTEGER));
        paramList.add(new SqlParameter(Types.VARCHAR));
        paramList.add(new SqlParameter(Types.VARCHAR));
        paramList.add(new SqlOutParameter("p_TotalRecordCount", Types.BIGINT));

        try
        {
            Map<String, Object> resultMap = cspJdbcTemplate.call(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(Connection connection) throws SQLException {

                    CallableStatement callableStatement = connection.prepareCall(procedureCall);
                    callableStatement.setLong("p_userAccountId", epReqDto.getAccountId());
                    callableStatement.setLong("p_supportAccountId", epReqDto.getSupportAccountId());
                    callableStatement.setLong("p_PaginationOffset", epReqDto.getPageOffset());
                    callableStatement.setInt("p_PaginationCount", epReqDto.getPageCount());
                    callableStatement.setString("p_Searchfield", epReqDto.getSearchField());
                    callableStatement.setString("p_SearchValue", epReqDto.getSearchValue());
                    callableStatement.registerOutParameter("p_TotalRecordCount", Types.BIGINT);
                    return callableStatement;
                }

            }, paramList);

            Object rs = resultMap.get("#result-set-1");
            if(rs != null)
            {
                List<LinkedCaseInsensitiveMap> list = (List<LinkedCaseInsensitiveMap>) rs;
                for(LinkedCaseInsensitiveMap map: list){
                    EpDevice device = new EpDevice();
                    device.setSerialNumber((String)map.get("SerialNumber"));
                    device.setAuthorizationCode((String)map.get("AuthorizationCode"));
                    device.setDescription((String)map.get("PartSubTypeName"));
                    device.setUseCount(map.get("UseCount") == null ? 0 : Integer.valueOf(map.get("UseCount").toString()));
                    device.setExpirationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(map.get("ExpirationDate").toString()));
                    device.setAscName((String)map.get("ASCName"));
                    device.setDeviceName((String)map.get("DeviceName"));
                    device.setDeviceTag((String)map.get("DeviceTag"));
                    device.setIsEsm(map.get("IsEsm") == null ? false : Boolean.valueOf(map.get("IsEsm").toString()));
                    response.addEpDevice(device);
                }
            }
            response.setTotalCount((long) resultMap.get("p_TotalRecordCount"));
        }catch (Exception e){

        }

        return response;
    }

    private void handleResultSet(ResultSet rs, EpResponseDto response) throws SQLException {
        while (rs.next()) {
            EpDevice device = new EpDevice();
            device.setSerialNumber(rs.getString("SerialNumber"));
            device.setAuthorizationCode(rs.getString("AuthorizationCode"));
            device.setDescription(rs.getString("PartSubTypeName"));
            device.setUseCount(rs.getInt("UseCount"));
            device.setExpirationDate(rs.getDate("ExpirationDate"));
            device.setAscName(rs.getString("ASCName"));
            device.setDeviceName(rs.getString("DeviceName"));
            device.setDeviceTag(rs.getString("DeviceTag"));
            device.setIsEsm(rs.getBoolean("IsEsm"));
            response.addEpDevice(device);
        }
        rs.close();
    }

    //
    public EpResponseDto getEpDevices2(EpRequestDto epReqDto) {
        String procedureCall;
        if(epReqDto.getSearchField() == null)
            procedureCall =
                String.format("call GetAvailableEPs(%d, %d, %d, %d, NULL, NULL, @total)",
                        epReqDto.getAccountId(), epReqDto.getSupportAccountId(), epReqDto.getPageOffset(), epReqDto.getPageCount());
        else
            procedureCall =
                    String.format("call GetAvailableEPs(%d, %d, %d, %d, '%s', '%s', @total)",
                            epReqDto.getAccountId(), epReqDto.getSupportAccountId(), epReqDto.getPageOffset(),
                            epReqDto.getPageCount(), epReqDto.getSearchField(), epReqDto.getSearchValue());
        List<EpDevice> devices = cspJdbcTemplate.query(procedureCall, new BeanPropertyRowMapper(EpDevice.class));
        EpResponseDto response = new EpResponseDto();
        response.setEps(devices);
        return response;
    }
}