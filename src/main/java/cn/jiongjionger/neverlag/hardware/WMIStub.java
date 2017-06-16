package cn.jiongjionger.neverlag.hardware;

import java.util.List;

interface WMIStub {

	String listClasses(String namespace, String computerName) throws WMIException;

	String listObject(String wmiClass, String namespace, String computerName) throws WMIException;

	String queryObject(String wmiClass, List<String> wmiProperties, List<String> conditions, String namespace, String computerName) throws WMIException;

	String listProperties(String wmiClass, String namespace, String computerName) throws WMIException;
}