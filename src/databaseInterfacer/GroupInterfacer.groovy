package databaseInterfacer

import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import exceptions.ResponseErrorCode
import exceptions.ResponseErrorException

class GroupInterfacer extends ClassInterfacer {
    def GroupInterfacer(factory) {
        super(factory, "Group", ["name", "domainData", "devices"])
    }

    void vertexNotFoundById(Long id) {
        throw new ResponseErrorException(ResponseErrorCode.GROUP_NOT_FOUND,
                404,
                "Group [" + id + "] was not found!",
                "The group does not exist")
    }

    void vertexNotFoundByIndex(String name) {
        throw new ResponseErrorException(ResponseErrorCode.GROUP_NOT_FOUND,
                404,
                "Group called [" + name + "] was not found!",
                "The group does not exist")
    }

    void duplicatedVertex() {
        throw new ResponseErrorException(ResponseErrorCode.DUPLICATES_FOUND,
                404,
                "Duplicated group found!",
                "The provided group already exist")
    }

    void invalidVertexProperties() {
        throw new ResponseErrorException(ResponseErrorCode.VALIDATION_ERROR,
                404,
                "Invalid group properties!",
                "The valid ones are " + this.fields)
    }

    protected final LinkedHashMap generateVertexProperties(HashMap data) {
        def areaName = data.name
        def domainData =  data.domainData

        return ["name": areaName,
                "domainData": domainData]
    }

    protected void generateVertexRelations(OrientVertex vertex, HashMap data) {
        def deviceNames = data.devices

        for (deviceName in deviceNames) {
            if (String.isInstance(deviceName) && !deviceName.isEmpty()) {
                OrientVertex device = getVerticesByIndex("name", deviceName, "Resource").getAt(0)
                if (device) {
                    vertex.addEdge("GroupsResource", device)
                } else {
                    throw new ResponseErrorException(ResponseErrorCode.DEVICE_NOT_FOUND,
                            404,
                            "Device [" + deviceName + "] was not found!",
                            "The device does not exist")
                }
            } else {
                invalidVertexProperties()
            }
        }
    }

    protected LinkedHashMap getExpandedVertex(OrientVertex vertex) {
        def deviceEdges = vertex.getEdges(Direction.OUT, "GroupsResource")
        def deviceNames = []
        if (deviceEdges)
            deviceEdges.each {
                def deviceName = it.getVertex(Direction.IN).getProperty("name")
                deviceNames.add(deviceName)
            }

        return ["devices": deviceNames]
    }
}