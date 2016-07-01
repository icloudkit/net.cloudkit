package net.cloudkit.enterprises.infrastructure.utilities;///*
// * Copyright (C) 2015 The CloudKit Open Source Project
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
//package net.cloudkit.ecological.enterprises.infrastructure.utilities;
//
//import org.apache.poi.POIXMLDocument;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.DataValidation;
//import org.apache.poi.ss.usermodel.DataValidationConstraint;
//import org.apache.poi.ss.usermodel.DataValidationHelper;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.ss.util.CellRangeAddressList;
//import org.apache.poi.xssf.usermodel.*;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PushbackInputStream;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//public class CommonUtility {
//
//    public synchronized static String getSerialNo() {
//        StringBuffer serialNoBuffer = new StringBuffer("M");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        serialNoBuffer.append(sdf.format(new Date()));
//        serialNoBuffer.append((int) (Math.random() * 9000) + 1000);
//        return serialNoBuffer.toString();
//    }
//
//    /**
//     * 复制行
//     *
//     * @param sheet 在其上进行操作的sheet
//     * @param pStartRow 源开始行
//     * @param pEndRow 源结束行
//     * @param pPosition 目标行的起始位置
//     */
//    public static void copyRows(XSSFSheet sheet, int pStartRow, int pEndRow, int pPosition) {
//        if ((pStartRow < 0) || (pEndRow <= 0)) {
//            return;
//        }
//
//        // 复制下拉框
//        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
//        List<XSSFDataValidation> dataValidations = sheet.getDataValidations();
//        for (XSSFDataValidation dataValidation : dataValidations) {
//            CellRangeAddress region = dataValidation.getRegions().getCellRangeAddress(0);
//            if ((region.getFirstRow() >= pStartRow) && (region.getLastRow() <= pEndRow)) {
//                int targetRowFrom = region.getFirstRow() - pStartRow + pPosition;
//                int targetRowTo = region.getLastRow() - pStartRow + pPosition;
//                region.setFirstRow(targetRowFrom);
//                region.setLastRow(targetRowTo);
//
//                CellRangeAddressList addressList = new CellRangeAddressList();
//                addressList.addCellRangeAddress(region);
//
//                DataValidationConstraint dvConstraint = dataValidation.getValidationConstraint();
//                DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
//                validation.setSuppressDropDownArrow(true);
//                validation.setShowErrorBox(true);
//                sheet.addValidationData(validation);
//            }
//        }
//
//        // 拷贝合并的单元格
//        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
//            CellRangeAddress region = sheet.getMergedRegion(i);
//            if ((region.getFirstRow() >= pStartRow) && (region.getLastRow() <= pEndRow)) {
//                int targetRowFrom = region.getFirstRow() - pStartRow + pPosition;
//                int targetRowTo = region.getLastRow() - pStartRow + pPosition;
//                region.setFirstRow(targetRowFrom);
//                region.setLastRow(targetRowTo);
//                sheet.addMergedRegion(region);
//            }
//        }
//
//        // 拷贝行并填充数据
//        for (int i = pStartRow; i <= pEndRow; i++) {
//            XSSFRow sourceRow = sheet.getRow(i);
//            if (sourceRow == null) {
//                continue;
//            }
//            XSSFRow targetRow = sheet.createRow(i - pStartRow + pPosition);
//            targetRow.setHeight(sourceRow.getHeight());
//
//            for (short j = sourceRow.getFirstCellNum(); j < sourceRow.getLastCellNum(); j++) {
//                XSSFCell sourceCell = sourceRow.getCell(j);
//                if (sourceCell == null) {
//                    continue;
//                }
//                XSSFCell targetCell = targetRow.createCell(j);
//                targetCell.setCellStyle(sourceCell.getCellStyle());
//                targetCell.setCellType(sourceCell.getCellType());
//                int cType = targetCell.getCellType();
//                switch (cType) {
//                    case XSSFCell.CELL_TYPE_BOOLEAN:
//                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
//                        break;
//                    case XSSFCell.CELL_TYPE_ERROR:
//                        targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
//                        break;
//                    case XSSFCell.CELL_TYPE_NUMERIC:
//                        targetCell.setCellValue(sourceCell.getNumericCellValue());
//                        break;
//                    case XSSFCell.CELL_TYPE_STRING:
//                        targetCell.setCellValue(sourceCell.getRichStringCellValue());
//                        break;
//                }
//            }
//
//            // 设置列宽
//            for (short j = sourceRow.getFirstCellNum(); j < sourceRow.getLastCellNum(); j++) {
//                sheet.setColumnWidth(j, sheet.getColumnWidth(j));
//            }
//        }
//    }
//
//    //判断版本
//    public synchronized static Workbook createWorkbook(InputStream inp) {
//        try {
//            if (!inp.markSupported()) {
//                inp = new PushbackInputStream(inp, 8);
//            }
//            if (POIFSFileSystem.hasPOIFSHeader(inp)) {
//                return new HSSFWorkbook(inp);
//            }
//            if (POIXMLDocument.hasOOXMLHeader(inp)) {
//                return new XSSFWorkbook(OPCPackage.open(inp));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public synchronized static boolean isDecimal(String value) {
//        return value.matches("\\d+") || value.matches("\\d+\\.\\d+");
//    }
//}
