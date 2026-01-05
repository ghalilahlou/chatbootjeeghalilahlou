package org.example.mcpserver.tools;

import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class McpTools {

    @McpTool(name = "getEmployee", description = "Get information about given employee")
    public Employee getEmployee(@McpArg(description = "the employee name") String name) {
        return new Employee(name, 12300, 4);
    }

    @McpTool(description = "Get all employees")
    public List<Employee> getAllEmployees() {
        return List.of(
                new Employee("ghali", 18000, 1),
                new Employee("sale", 12000, 1),
                new Employee("yamine", 28000, 3)
        );
    }

    public record Employee(String name, double salary, int seniority) {}
}

