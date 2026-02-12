/**
 * Default property overrides for Vuetify components.
 * Ensures a consistent look and feel across the application without repeating props.
 */

export default {
  /** Default style for icon buttons */
  IconBtn: {
    icon: true,
    color: 'default',
    variant: 'text',
  },
  /** Global override for alerts to prevent inheritance issues */
  VAlert: {
    VBtn: {
      color: undefined,
    },
  },
  /** Common style for avatars */
  VAvatar: {
    variant: 'flat',
  },
  /** Default badges use the primary color */
  VBadge: {
    color: 'primary',
  },
  /** Primary buttons configuration */
  VBtn: {
    color: 'primary',
  },
  /** Chip styling for consistent tagging */
  VChip: {
    elevation: 0,
    style: {
      width: 'max-content',
    }
  },
  /** Logic for grouping selection chips */
  VChipGroup:{
    column: true,
    selectedClass: '',
  },
  /** Menus position offset from activator */
  VMenu: {
    offset: '2px',
  },
  /** Standard pagination configuration for lists */
  VPagination: {
    density: 'comfortable',
    showFirstLastPage: true,
    variant: 'tonal',
  },
  /** Tabs and slide groups navigation UI */
  VTabs: {
    color: 'primary',
    VSlideGroup: {
      showArrows: true,
    },
  },
  /** Tooltips appear above elements by default */
  VTooltip: {
    location: 'top',
  },
  /** Form internal checkbox button styling */
  VCheckboxBtn: {
    color: 'primary',
  },
  /** Standard form checkbox styling */
  VCheckbox: {
    color: 'primary',
    density: 'comfortable',
    hideDetails: 'auto',
  },
  /** Standard form radio group styling */
  VRadioGroup: {
    color: 'primary',
    density: 'comfortable',
    hideDetails: 'auto',
  },
  /** Single radio element styling */
  VRadio: {
    density: 'comfortable',
    hideDetails: 'auto',
  },
  /** Global select/dropdown styling (outlined by default) */
  VSelect: {
    variant: 'outlined',
    color: 'primary',
    hideDetails: 'auto',
    density: 'comfortable',
  },
  /** Range slider visuals for filters */
  VRangeSlider: {
    color: 'primary',
    thumbLabel: true,
    hideDetails: 'auto',
    trackSize: 6,
    thumbSize: 22,
    elevation: 4,
  },
  /** Star rating components */
  VRating: {
    activeColor: 'warning',
    color: 'disabled',
  },
  /** Linear loading indicators */
  VProgressLinear: {
    color: 'primary',
  },
  /** Standard continuous sliders */
  VSlider: {
    color: 'primary',
    trackSize: 6,
    hideDetails: 'auto',
    thumbSize: 22,
    elevation: 4,
  },
  /** Global snackbar configuration (notifications) */
  VSnackbar: {
    VBtn: {
      size: 'small',
    },
  },
  /** Standard text field appearance */
  VTextField: {
    variant: 'outlined',
    density: 'comfortable',
    color: 'primary',
  },
  /** Autocomplete components styling */
  VAutocomplete: {
    variant: 'outlined',
    color: 'primary',
    density: 'comfortable',
    hideDetails: 'auto',
  },
  /** Combobox components styling */
  VCombobox: {
    variant: 'outlined',
    color: 'primary',
    hideDetails: 'auto',
    density: 'comfortable',
  },
  /** File upload input styling */
  VFileInput: {
    variant: 'outlined',
    color: 'primary',
    hideDetails: 'auto',
    density: 'comfortable',
  },
  /** Multi-line text area styling */
  VTextarea: {
    variant: 'outlined',
    color: 'primary',
    hideDetails: 'auto',
    density: 'comfortable',
  },
  /** Toggle switches configuration */
  VSwitch: {
    // set v-switch default color to primary
    inset: true,
    color: 'primary',
    hideDetails: 'auto',
  },
  /** Navigation drawer accessibility settings */
  VNavigationDrawer: {
    touchless: true,
  },
  /** Global overlay and loading screen appearance */
  VOverlay: {
    scrim: 'background',
    opacity: 0.7,
  },
  /** Standard dialog modal background (scrim) */
  VDialog: {
    scrim: 'background'
  },
  /** Global card rounding style */
  VCard: {
    rounded: 'lg'
  },
  /** Date picker input styling (Labs) */
  VDateInput: {
    variant: 'outlined',
  },
  /** Horizontal and vertical divider thickness */
  VDivider: {
    thickness: 2,
  },
}
