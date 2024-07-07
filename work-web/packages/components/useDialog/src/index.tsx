import {
  render,
  getCurrentInstance,
  unref,
  type Component,
  type ComponentInternalInstance,
  type VNode,
  type AppContext,
  inject,
  nextTick,
  ref,
  watch,
} from "vue";
import { ElDialog, ElButton, type DialogProps, ElScrollbar, ElConfigProvider } from "element-plus";
import { getPx } from "@work/utils";
import { Icon } from "@work/components";
import "./index.scss";
import { useDesign } from "@work/hooks";
import { ConfigGlobalKey } from "@work/constants";

const { getPrefixClass, variables } = useDesign();
const prefixClass = getPrefixClass("work-dialog");

let id = 0;

let appContextConst: AppContext | undefined;
let layoutSize: "default" | "small" | "large" | undefined;

const getFather = (): Element => {
  const fullScreen = document.querySelector(":not(:root):fullscreen");
  if (fullScreen) return fullScreen;
  return document.querySelector("body") as HTMLBodyElement;
};

export interface WorkDialogProps extends Partial<DialogProps> {
  render?: () => VNode | string; // 内容渲染 TSX
  headerRender?: (scope: any) => VNode; // 头部渲染 TSX
  footerRender?: () => VNode; // 底部渲染 TSX
  showFooter?: boolean; // 是否渲染底部，默认 true
  onConfirm?: (closeDialog: () => void) => any; // 确认按钮点击事件
  onClose?: (closeDialog: () => void) => any; // 关闭按钮点击事件
  confirmLabel?: string; // 确认按钮文字，默认 确认
  closeLabel?: string; // 关闭按钮文字，默认 关闭
  fullscreen?: boolean; // 是否默认全屏，默认 false
  fullscreenIcon?: boolean; // 是否渲染全屏图标，默认 true
  height?: string | number; // 内容高度，默认 400px
}

/**
 * @description 关闭弹框
 */
export const closeDialog = () => {
  const vm = document.querySelector(`#${prefixClass}-${id--}`) as HTMLElement;
  vm && getFather().removeChild(vm);
};

const handleClose = async (dialogProps?: WorkDialogProps) => {
  if (!dialogProps?.onClose) return closeDialog();

  const result = await dialogProps?.onClose(closeDialog);
  if (result || result === 0) return closeDialog();
};

const handleConfirm = async (dialogProps?: WorkDialogProps) => {
  if (!dialogProps?.onConfirm) return closeDialog();

  const result = await dialogProps?.onConfirm(closeDialog);

  if (result || result === 0) return closeDialog();
};

/**
 * 内容渲染方式有两种
 * 方式 1：在第一个参数里写 render，即可实现 el-dialog 的内容渲染
 * 方式 2：第二个参数为组件，第三个参数为组件的 props
 *
 * 在第一个参数里写 headerRender 和 footerRender，可以自定义 el-dialog 的 header 和 footer
 */
export const showDialog = (dialogProps: WorkDialogProps, component?: Component, componentsProps?: any) => {
  const isFullscreen = ref(dialogProps.fullscreen || false);

  const toggleFull = () => {
    const elDialogEl = document.querySelector(
      `${`#${prefixClass}-${id}`} .${prefixClass}.${variables.elNamespace}-dialog`
    ) as HTMLElement;
    if (elDialogEl) elDialogEl.classList.toggle("is-fullscreen");
    isFullscreen.value = !unref(isFullscreen);
  };

  const contentHeight = ref(getPx(dialogProps.height || 400));

  watch(
    () => unref(isFullscreen),
    async (val: boolean) => {
      await nextTick();
      if (val) {
        const windowHeight = document.documentElement.offsetHeight;
        // 头部高度 41px，顶部 padding-bottom 16px，内容区 padding 上下各 15，底部高度 49px，顶部 padding-top 16px
        contentHeight.value = `${windowHeight - 41 - 16 - 30 - 49 - 16 - (dialogProps.footerRender ? 63 : 0)}px`;
      } else {
        contentHeight.value = getPx(dialogProps.height || 400);
      }
    },
    {
      immediate: true,
    }
  );

  const vm = (
    <ElConfigProvider namespace={variables.elNamespace} size={unref(layoutSize)}>
      <ElDialog
        modelValue
        title="弹框"
        top="2vh"
        width="50%"
        before-close={() => handleClose(dialogProps)}
        close-on-click-modal={false}
        draggable
        {...dialogProps}
        render
        headerRender
        footerRender
        class={prefixClass}
      >
        {{
          default: () => {
            if (dialogProps.render) {
              return (
                <ElScrollbar
                  style={{
                    height: unref(contentHeight),
                  }}
                >
                  {dialogProps.render()}
                </ElScrollbar>
              );
            }
            return (
              <ElScrollbar style={{ height: unref(contentHeight) }}>
                <component is={component} {...componentsProps}></component>
              </ElScrollbar>
            );
          },
          header: (scope: any) => {
            if (dialogProps?.headerRender) return dialogProps.headerRender(scope);
            return (
              <div style="display: flex">
                <span class={`${variables.elNamespace}-dialog__title`} style="flex: 1">
                  {dialogProps.title}
                </span>
                {dialogProps.fullscreenIcon !== false && (
                  <Icon
                    name={unref(isFullscreen) ? "fullscreen-exit" : "fullscreen"}
                    onClick={() => toggleFull()}
                    width="15px"
                    height="15px"
                    color={`var(--${variables.elNamespace}-color-info)`}
                    hover-color={`var(--${variables.elNamespace}-color-primary)`}
                    icon-style={{ cursor: "pointer" }}
                  />
                )}
              </div>
            );
          },
          footer: () => {
            if (dialogProps.footerRender) return dialogProps.footerRender();
            if (dialogProps.showFooter === false) return;
            return (
              <>
                <ElButton onClick={() => handleClose(dialogProps)}>{dialogProps.closeLabel || "取 消"}</ElButton>
                <ElButton type="primary" onClick={() => handleConfirm(dialogProps)}>
                  {dialogProps.confirmLabel || "确 定"}
                </ElButton>
              </>
            );
          },
        }}
      </ElDialog>
    </ElConfigProvider>
  );

  vm.appContext = appContextConst;
  vm.children?.length && (vm.children[0].appContext = appContextConst);

  const container = document.createElement("div");
  container.id = `${prefixClass}-${++id}`;
  getFather().appendChild(container);
  render(vm, container);
};

export const initDialog = (ctx?: ComponentInternalInstance) => {
  const { appContext } = ctx || getCurrentInstance() || {};
  appContextConst = appContext;
  layoutSize = unref(inject(ConfigGlobalKey)?.size);

  return { showDialog };
};
