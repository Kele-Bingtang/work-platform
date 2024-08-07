import { defineStore } from "pinia";
import type { LayoutModeType, LayoutThemeType, TabsNavModeType } from ".";
import defaultSettings from "@/config/settings";
import { ref } from "vue";
import { useStorage } from "@work/hooks";
import { useCache } from "@/hooks";

const {
  primaryTheme: primaryThemeSetting,
  titleMode: titleModeSetting,
  layoutMode: layoutModeSetting,
  tabsNavMode: tabsNavModeSetting,
  layoutTheme: layoutThemeSetting,
  showSettings: showSettingsSetting,
  showTabsNav: showTagsNavSetting,
  recordTabsNav: recordTagsNavSetting,
  showLayoutLogo: showLayoutLogoSetting,
  showBreadcrumb: showBreadcrumbSetting,
  showBreadcrumbIcon: showBreadcrumbIconSetting,
  showTabsNavIcon: tabsNavSetting,
  isCollapse: isCollapseSetting,
  menuAccordion: menuAccordionSetting,
  fixTabsNav: fixTabsNavSetting,
  isDark: isDarkSetting,
  isWeak: isWeakSetting,
  isGrey: isGreySetting,
  maximize: maximizeSetting,
  menuWidth: menuWidthSetting,
  headerHeight: headerHeightSetting,
} = defaultSettings;

export const useSettingsStore = defineStore(
  "settingsStore",
  () => {
    const primaryTheme = ref(primaryThemeSetting);
    const titleMode = ref(titleModeSetting);
    const layoutMode = ref<LayoutModeType>(layoutModeSetting);
    const tabsNavMode = ref<TabsNavModeType>(tabsNavModeSetting);
    const menuTheme = ref<LayoutThemeType>(layoutThemeSetting);
    const showSettings = ref(showSettingsSetting);
    const showTabsNav = ref(showTagsNavSetting);
    const recordTabsNav = ref(recordTagsNavSetting);
    const showLayoutLogo = ref(showLayoutLogoSetting);
    const showBreadcrumb = ref(showBreadcrumbSetting);
    const showBreadcrumbIcon = ref(showBreadcrumbIconSetting);
    const showTabsNavIcon = ref(tabsNavSetting);
    const isCollapse = ref(isCollapseSetting);
    const menuAccordion = ref(menuAccordionSetting);
    const fixTabsNav = ref(fixTabsNavSetting);
    const isDark = ref(isDarkSetting);
    const isWeak = ref(isWeakSetting);
    const isGrey = ref(isGreySetting);
    const headerTheme = ref<LayoutThemeType>(layoutThemeSetting);
    const maximize = ref(maximizeSetting);
    const menuWidth = ref(menuWidthSetting);
    const headerHeight = ref(headerHeightSetting);

    const closeSideMenu = () => {
      isCollapse.value = true;
    };

    const toggleSideMenu = () => {
      isCollapse.value = !isCollapse.value;
    };

    const resetSettings = () => {
      const { removeStorage } = useStorage("localStorage");
      removeStorage(`${defaultSettings.cacheKeyPrefix}_settingsStore`);
      if (!recordTabsNav.value) useCache().removeCacheTabNavList();
    };

    return {
      primaryTheme,
      titleMode,
      layoutMode,
      tabsNavMode,
      menuTheme,
      showSettings,
      showTabsNav,
      recordTabsNav,
      showLayoutLogo,
      showBreadcrumb,
      showBreadcrumbIcon,
      showTabsNavIcon,
      isCollapse,
      menuAccordion,
      fixTabsNav,
      isDark,
      isWeak,
      isGrey,
      headerTheme,
      maximize,
      menuWidth,
      headerHeight,

      closeSideMenu,
      toggleSideMenu,
      resetSettings,
    };
  },
  {
    persist: true,
  }
);
