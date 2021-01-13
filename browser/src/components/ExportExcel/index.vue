<template>
  <transition name="fadeIn">
    <div v-show="true" style="margin: auto">
      <el-table
        :id="exportExcelInfo.excelId"
        :data="tableData"
        border
        max-height="450"
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column
          v-for="item in exportExcelArr"
          :key="item.label"
          :prop="item.prop"
          :label="item.label"
        >
        </el-table-column>
      </el-table>
    </div>
  </transition>
</template>

<script>
import FileSaver from "file-saver";
import XLSX from "xlsx";

export default {
  props: {
    exportExcelInfo: {
      type: Object,
      default: {},
    },
    exportExcelArr: {
      type: Array,
      default: [],
    },
    tableData: {
      type: Array,
      default: [],
    },
  },
  methods: {
    //excel导出
    exportExcel() {
      var xlsxParam = { raw: true }; // 导出的内容只做解析，不进行格式转换
      var wb = XLSX.utils.table_to_book(
        document.querySelector("#" + this.exportExcelInfo.excelId),
        xlsxParam
      );
      var wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array",
      });
      try {
        FileSaver.saveAs(
          new Blob([wbout], { type: "application/octet-stream" }),
          this.exportExcelInfo.excelName
        );
      } catch (e) {
        if (typeof console !== "undefined") console.log(e, wbout);
      }
      return wbout;
    },
  },
};
</script>