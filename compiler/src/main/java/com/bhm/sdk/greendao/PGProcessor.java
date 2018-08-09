/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 baoyongzhang <baoyz94@gmail.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.bhm.sdk.greendao;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javawriter.JavaWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.EnumSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * ParcelableGenerator
 * Created by baoyz on 15/6/24.
 * alter by bhm on 18/8/9
 */

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("com.bhm.sdk.greendao.Parcelable")
public class PGProcessor extends AbstractProcessor{

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        filer = env.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        try {
            javaPoet();
            testJavaWriter();

            //编译期间，在build/generated/apt/debug/包下生成java代码
            Set<? extends Element> set = roundEnv
                    .getElementsAnnotatedWith(Parcelable.class);
            for (Element element : set) {
                try {
                    TypeElement enclosingElement = (TypeElement) element;
                    ProxyInfo pi = new ProxyInfo(enclosingElement
                            .getQualifiedName().toString());
                    writeLog(pi.getFullName());
                    JavaFileObject jfo = filer.createSourceFile(
                            pi.getFullName(), enclosingElement);
                    Writer writer = jfo.openWriter();
                    writeLog(pi.createCode());
                    writer.write(pi.createCode());
                    writer.flush();
                    writer.close();
                    writeLog("ok");
                } catch (Exception e) {
                    e.printStackTrace();
                    writeLog(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeLog(e.getMessage());
        }
        return true;
    }

    private void writeLog(String str) {
        // try {
        // FileWriter fw = new FileWriter(new File("D:/process.txt"), true);
        // fw.write(str + "\n");
        // fw.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    /** 编译期间，在build/generated/apt/debug/包下生成java代码
     * @throws Exception
     */
    private void javaPoet() throws Exception{
        // main method
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();
        // HelloWorld class
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        // build com.example.HelloWorld.java
        JavaFile javaFile = JavaFile.builder("com.bhm.sdk.greendao.demo", helloWorld)
                .addFileComment(" This codes are generated automatically. Do not modify!")
                .build();
        // write to file
        javaFile.writeTo(filer);
    }

    /**编译期间，在src/main/java指定包下生成java代码
     * @throws IOException
     */
    private void testJavaWriter() throws IOException {
        String packageName = "com.bhm.sdk.greendao.demo.entity";
        String className = "GenerateEntity";
        File outFile = new File("demo/src/main/java/" + packageName.replaceAll("\\.", "/") + "/" + className + ".java");
        if(!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFile));
        JavaWriter jw = new JavaWriter(writer);
        jw.emitPackage(packageName)
                .emitJavadoc("This codes are generated automatically. Do not modify!")
                .beginType(packageName + "." + className, "class", EnumSet.of(Modifier.PUBLIC, Modifier.FINAL))
                .emitField("String", "firstName", EnumSet.of(Modifier.PRIVATE))
                .emitField("String", "lastName", EnumSet.of(Modifier.PRIVATE))
                .emitJavadoc("Return the person's full name")
                .beginMethod("String", "getName", EnumSet.of(Modifier.PUBLIC))
                .emitStatement("return firstName + \" - \" + lastName")
                .endMethod()
                .beginMethod("String", "getFirstName", EnumSet.of(Modifier.PUBLIC))
                .emitStatement("return firstName")
                .endMethod()
                .beginMethod("String", "getLastName", EnumSet.of(Modifier.PUBLIC))
                .emitStatement("return lastName")
                .endMethod()
                .endType()
                .close();
    }
}
