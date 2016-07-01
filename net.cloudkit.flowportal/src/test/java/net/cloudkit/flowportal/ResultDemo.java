///*
// * Copyright (C) 2015. The CloudKit Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package net.cloudkit.flowportal;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//
//public class ResultDemo {
//	public static void main(String[] args) throws Exception{
//		File file = new File("d://5s.pdf");
//		OutputStream out = new FileOutputStream(file);
//		String sourcepath = "d://55.pdf";
//		String icconpath = "d://2a.jpg";
//		int result = -1;
//		if(sourcepath.lastIndexOf(".") != -1 ){
//			String fileType = sourcepath.substring(sourcepath.lastIndexOf(".")+1, sourcepath.length());
//			if(fileType.equals("pdf")){
//				result = new PdfWaterMarkUtil().PdfWaterMarkUtil(sourcepath, icconpath,out,50,100);
//			} else if(".jpg.jpeg.png.gif.bmp".contains(fileType.toLowerCase())){
//				result = new ImgWaterMarkUtil().ImgWaterMarkUtil(sourcepath, icconpath, out,50,100);
//			}
//		}
//	}
//}
