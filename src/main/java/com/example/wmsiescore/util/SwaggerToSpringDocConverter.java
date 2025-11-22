package com.example.wmsiescore.util;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Swagger to SpringDoc OpenAPI annotation converter utility class
 * Used to batch replace Swagger annotations with SpringDoc OpenAPI annotations in the project
 */
public class SwaggerToSpringDocConverter {
    
    /**
     * 执行转换
     * @param projectPath 项目路径
     * @throws IOException IO异常
     */
    public static void convert(String projectPath) throws IOException {
        Path srcPath = Paths.get(projectPath, "src/main/java");
        
        // Find all Java files
        List<Path> javaFiles = Files.walk(srcPath)
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".java"))
            .collect(Collectors.toList());
        
        System.out.println("Found " + javaFiles.size() + " Java files");
        
        int modifiedCount = 0;
        
        for (Path file : javaFiles) {
            // 读取文件内容
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(file)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
            }
            String content = contentBuilder.toString();
            String originalContent = content;
            
            // 替换import语句
            content = content.replaceAll("import io\\.swagger\\.annotations\\.Api;", "import io.swagger.v3.oas.annotations.tags.Tag;");
            content = content.replaceAll("import io\\.swagger\\.annotations\\.ApiOperation;", "import io.swagger.v3.oas.annotations.Operation;");
            content = content.replaceAll("import io\\.swagger\\.annotations\\.ApiParam;", "import io.swagger.v3.oas.annotations.Parameter;");
            content = content.replaceAll("import io\\.swagger\\.annotations\\.ApiModel;", "import io.swagger.v3.oas.annotations.media.Schema;");
            content = content.replaceAll("import io\\.swagger\\.annotations\\.ApiModelProperty;", "import io.swagger.v3.oas.annotations.media.Schema;");
            content = content.replaceAll("import io\\.swagger\\.annotations\\.(.*?);", "import io.swagger.v3.oas.annotations.$1;");
            
            // 替换@Api注解
            content = content.replaceAll("@Api\\s*\\(\\s*tags\\s*=\\s*\"(.*?)\"\\s*,\\s*description\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Tag(name = \"$1\", description = \"$2\")");
            content = content.replaceAll("@Api\\s*\\(\\s*tags\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Tag(name = \"$1\")");
            
            // 替换@ApiOperation注解
            content = content.replaceAll("@ApiOperation\\s*\\(\\s*value\\s*=\\s*\"(.*?)\"\\s*,\\s*notes\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Operation(summary = \"$1\", description = \"$2\")");
            content = content.replaceAll("@ApiOperation\\s*\\(\\s*value\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Operation(summary = \"$1\")");
            
            // 替换@ApiParam注解
            content = content.replaceAll("@ApiParam\\s*\\(\\s*value\\s*=\\s*\"(.*?)\"\\s*,\\s*required\\s*=\\s*(true|false)\\s*\\)", 
                                        "@Parameter(description = \"$1\", required = $2)");
            content = content.replaceAll("@ApiParam\\s*\\(\\s*value\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Parameter(description = \"$1\")");
            
            // 替换@Schema注解
            content = content.replaceAll("@Schema\\s*\\(\\s*value\\s*=\\s*\"(.*?)\"\\s*,\\s*description\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Schema(description = \"$2\")");
            content = content.replaceAll("@Schema\\s*\\(\\s*description\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Schema(description = \"$1\")");
            content = content.replaceAll("@Schema\\s*\\(\\s*value\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Schema(description = \"$1\")");
            content = content.replaceAll("@Schema", "@Schema");
            
            // 替换@Schema注解
            content = content.replaceAll("@Schema\\s*\\(\\s*value\\s*=\\s*\"(.*?)\"\\s*,\\s*example\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Schema(description = \"$1\", example = \"$2\")");
            content = content.replaceAll("@Schema\\s*\\(\\s*value\\s*=\\s*\"(.*?)\"\\s*\\)", 
                                        "@Schema(description = \"$1\")");
            content = content.replaceAll("@Schema", "@Schema");
            
            // 如果内容有变化，写回文件
            if (!content.equals(originalContent)) {
                try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                    writer.write(content);
                }
                System.out.println("Modified: " + file);
                modifiedCount++;
            }
        }
        
        System.out.println("Total modified files: " + modifiedCount);
    }
    
    /**
     * Fix SchemaProperty annotation issues
     * @param projectPath Project path
     * @throws IOException IO exception
     */
    public static void fixSchemaProperty(String projectPath) throws IOException {
        Path srcPath = Paths.get(projectPath, "src/main/java");
        
        // Find all Java files
        List<Path> javaFiles = Files.walk(srcPath)
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".java"))
            .collect(Collectors.toList());
        
        System.out.println("Found " + javaFiles.size() + " Java files");
        
        int modifiedCount = 0;
        
        for (Path file : javaFiles) {
            // 读取文件内容
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(file)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
            }
            String content = contentBuilder.toString();
            String originalContent = content;
            
            // 修复重复的import
            content = content.replaceAll("import io\\.swagger\\.v3\\.oas\\.annotations\\.media\\.Schema;\\s*import io\\.swagger\\.v3\\.oas\\.annotations\\.media\\.Schema;", 
                                        "import io.swagger.v3.oas.annotations.media.Schema;");
            
            // 替换SchemaProperty为Schema
            content = content.replaceAll("@Schema", "@Schema");
            
            // 替换value=为description=
            content = content.replaceAll("@Schema\\s*\\(\\s*value\\s*=", "@Schema(description =");
            
            // 如果内容有变化，写回文件
            if (!content.equals(originalContent)) {
                try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                    writer.write(content);
                }
                System.out.println("Modified: " + file);
                modifiedCount++;
            }
        }
        
        System.out.println("Total modified files: " + modifiedCount);
    }
    
    /**
     * 主方法，用于独立运行转换工具
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        try {
            String projectPath = "d:/WorkSpace/JavaProject/wms-ies-core";
            
            System.out.println("Starting to convert Swagger annotations to SpringDoc OpenAPI annotations...");
            convert(projectPath);
            
            System.out.println("\nStarting to fix SchemaProperty annotation issues...");
            fixSchemaProperty(projectPath);
            
            System.out.println("\nConversion completed!");
        } catch (IOException e) {
            System.err.println("Error occurred during conversion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
