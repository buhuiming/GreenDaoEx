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

/**
 * ParcelableGenerator
 * Created by baoyz on 15/6/24.
 */
public class ProxyInfo {

    private static final String SUFFIX = "$$Parcelable";

    private String packageName;
    private String proxyName;
    private String className;

    public ProxyInfo(String qualifiedName) {
        super();
        packageName = qualifiedName
                .substring(0, qualifiedName.lastIndexOf("."));
        className = qualifiedName.substring(packageName.length() + 1);
        proxyName = className + SUFFIX;
    }

    public String createCode() {
        return ProxyTemplate.createCode(packageName, proxyName, className);
    }

    public String getFullName() {
        return packageName + "." + proxyName;
    }
}
