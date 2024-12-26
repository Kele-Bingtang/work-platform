import type { Component } from "vue";
import {
  ElCascader,
  ElCheckbox,
  ElCheckboxGroup,
  ElColorPicker,
  ElDatePicker,
  ElInput,
  ElInputNumber,
  ElRadio,
  ElRadioGroup,
  ElRate,
  ElSelect,
  ElSelectV2,
  ElSlider,
  ElSwitch,
  ElTimePicker,
  ElTimeSelect,
  ElTransfer,
  ElAutocomplete,
  ElDivider,
  ElTreeSelect,
  ElUpload,
} from "element-plus";
import type { PascalCaseComponentName } from "../interface";
import Tree from "../components/Tree.vue";
import CheckBoxSelect from "../components/CheckBoxSelect.vue";
import { WangEditor } from "@work/components/WangEditor";
import { Tinymce } from "@work/components/Tinymce";
import { UserSelect } from "@work/components/UserSelect";
import { IconPicker } from "@work/components/iconPicker";
import { CodeMirror } from "@work/components/codeMirror";

const componentMap: Record<PascalCaseComponentName, Component> = {
  ElInput,
  ElInputNumber,
  ElSelect,
  ElSelectV2,
  ElTree: Tree,
  ElTreeSelect,
  ElCascader,
  ElDatePicker,
  ElTimePicker,
  ElTimeSelect,
  ElSwitch,
  ElSlider,
  ElRadio,
  ElRadioGroup,
  ElRadioButton: ElRadioGroup,
  ElCheckbox,
  ElCheckboxGroup,
  ElCheckboxButton: ElCheckboxGroup,
  ElAutocomplete,
  ElRate,
  ElColorPicker,
  ElTransfer,
  ElDivider,
  ElUpload,
  CheckBoxSelect,
  WangEditor,
  Tinymce,
  UserSelect,
  IconPicker,
  CodeMirror,
};

export { componentMap };
