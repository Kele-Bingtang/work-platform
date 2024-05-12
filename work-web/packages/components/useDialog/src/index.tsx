import { render, ref, getCurrentInstance, type Component, type ComponentInternalInstance, type VNode } from "vue";
import { ElDialog, ElButton, type DialogProps } from "element-plus";

const id = ref(0);
let thisAppContext: any = null;

const getFather = (): Element => {
  const fullScreen = document.querySelector(":not(:root):fullscreen");
  if (fullScreen) return fullScreen;
  return document.querySelector("body") as HTMLBodyElement;
};

interface Dialog extends Partial<DialogProps> {
  render?: () => VNode;
  headerRender?: () => VNode;
  showFooter?: boolean;
  footerRender?: () => VNode;
  onConfirm?: (closeDialog: () => void) => void;
  onClose?: (closeDialog: () => void) => void;
}

export const closeDialog = () => {
  const vm = document.querySelector(`#work-dialog-${id.value}`) as Element;
  getFather().removeChild(vm);
};

const handleClose = (dialogProps?: Dialog) => {
  if (dialogProps?.onClose) dialogProps?.onClose(closeDialog);
  return closeDialog();
};

const handleConfirm = (dialogProps?: Dialog) => {
  if (dialogProps?.onConfirm) dialogProps?.onConfirm(closeDialog);
  return closeDialog();
};

/**
 * 内容渲染方式有两种
 * 方式 1：在第一个参数里写 render，即可实现 el-dialog 的内容渲染
 * 方式 2：第二个参数为组件，第三个参数为组件的 props
 *
 * 在第一个参数里写 headerRender 和 footerRender，可以自定义 el-dialog 的 header 和 footer
 */
export const showDialog = (
  dialogProps?: Dialog,
  component?: Component,
  componentsProps?: any,
  ctx?: ComponentInternalInstance
) => {
  ctx && (thisAppContext = ctx?.appContext);

  const vm = (
    <div id={`work-dialog-${++id.value}`} key={id.value}>
      <ElDialog
        modelValue
        title="弹框"
        top="2vh"
        width="50%"
        before-close={() => handleClose(dialogProps)}
        close-on-click-modal={false}
        {...dialogProps}
      >
        {{
          default: () => {
            if (dialogProps?.render) return dialogProps?.render();
            return <component is={component} {...componentsProps}></component>;
          },
          header: () => {
            if (dialogProps?.headerRender) return dialogProps?.headerRender();
          },
          footer: () => {
            if (dialogProps?.footerRender) return dialogProps?.footerRender();
            if (dialogProps?.showFooter === false) return;
            return (
              <>
                <ElButton onClick={() => handleClose(dialogProps)}>取 消</ElButton>
                <ElButton type="primary" onClick={() => handleConfirm(dialogProps)}>
                  确 定
                </ElButton>
              </>
            );
          },
        }}
      </ElDialog>
    </div>
  );

  vm.appContext = thisAppContext;
  vm.children?.length && (vm.children[0].appContext = thisAppContext);
  render(vm, getFather());
};

export const initDialog = (ctx?: ComponentInternalInstance) => {
  const { appContext } = ctx || (getCurrentInstance() as ComponentInternalInstance);
  thisAppContext = appContext;
  return { showDialog };
};
