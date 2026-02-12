/**
 * Icon configuration for Vuetify.
 * Maps Material Design Icons (MDI) and Remix Icons (RI) to Vuetify's internal aliases
 * and handles custom SVG icons (checkboxes, logos, etc.).
 */

import type { IconAliases, IconProps } from 'vuetify'
import checkboxChecked from '@/assets/images/svg/checkbox-checked.svg'
import checkboxIndeterminate from '@/assets/images/svg/checkbox-indeterminate.svg'
import checkboxUnchecked from '@/assets/images/svg/checkbox-unchecked.svg'
import radioChecked from '@images/svg/radio-checked.svg'
import radioUnchecked from '@images/svg/radio-unchecked.svg'
import calendarStart from '@/assets/images/svg/calendar-start.svg'
import calendarEnd from '@/assets/images/svg/calendar-end.svg'
import magnoLogo from '@/assets/images/logos/magno-dark.svg'
import { h } from 'vue'

/**
 * Registry of custom SVG components mapped to icon name strings.
 */
const customIcons: Record<string, unknown> = {
  'mdi-checkbox-blank-outline': checkboxUnchecked,
  'mdi-checkbox-marked': checkboxChecked,
  'mdi-minus-box': checkboxIndeterminate,
  'mdi-radiobox-marked': radioChecked,
  'mdi-radiobox-blank': radioUnchecked,
  'ri-calendar-start': calendarStart,
  'ri-calendar-end': calendarEnd,
  'ri-app-logo': magnoLogo
}

/**
 * Maps standard Vuetify icon names (info, success, etc.) to specific Remix Icon classes.
 */
const aliases: Partial<IconAliases> = {
  info: 'ri-error-warning-line',
  success: 'ri-checkbox-circle-line',
  warning: 'ri-alert-line',
  error: 'ri-error-warning-line',
  calendar: 'ri-calendar-2-line',
  collapse: 'ri-arrow-up-s-line',
  complete: 'ri-check-line',
  cancel: 'ri-close-line',
  close: 'ri-close-line',
  delete: 'ri-close-circle-fill',
  clear: 'ri-close-line',
  prev: 'ri-arrow-left-s-line',
  next: 'ri-arrow-right-s-line',
  delimiter: 'ri-checkbox-blank-circle-line',
  sort: 'ri-arrow-up-line',
  expand: 'ri-arrow-down-s-line',
  menu: 'ri-menu-line',
  subgroup: 'ri-arrow-down-s-fill',
  dropdown: 'ri-arrow-down-s-line',
  edit: 'ri-pencil-line',
  ratingEmpty: 'ri-star-line',
  ratingFull: 'ri-star-fill',
  ratingHalf: 'ri-star-half-line',
  loading: 'ri-refresh-line',
  first: 'ri-skip-back-mini-line',
  last: 'ri-skip-forward-mini-line',
  unfold: 'ri-split-cells-vertical',
  file: 'ri-attachment-2',
  plus: 'ri-add-line',
  minus: 'ri-subtract-line',
  sortAsc: 'ri-arrow-up-line',
  sortDesc: 'ri-arrow-down-line',
}

/**
 * Custom icon set implementation that uses Iconify-style class names.
 * Intercepts custom SVG icons before falling back to class-based rendering.
 */
export const iconify = {
  /**
   * Render function for icons.
   * @param {IconProps} props - Properties passed to the icon component.
   * @returns {VNode} The rendered icon.
   */
  component: (props: IconProps) => {
    // Load custom SVG directly instead of going through icon component
    if (typeof props.icon === 'string') {
      const iconComponent = customIcons[props.icon]

      if (iconComponent)
        return h(iconComponent)
    }

    return h(
      props.tag,
      {
        ...props,

        // As we are using class based icons
        class: [props.icon],

        // Remove used props from DOM rendering
        tag: undefined,
        icon: undefined,
      },
    )
  },
}

/**
 * Vuetify icon configuration object.
 */
export const icons = {
  defaultSet: 'iconify',
  aliases,
  sets: {
    iconify,
  },
}
