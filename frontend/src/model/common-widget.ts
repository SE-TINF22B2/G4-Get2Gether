export type BaseWidget = {
  id: string;
  creationDate: string;
  widgetType: WidgetType;
}

export enum WidgetType {
  SHOPPING_LIST = "SHOPPING_LIST",
  MAP = "MAP",
  EXPENSE_SPLIT = "EXPENSE_SPLIT",
  CARPOOL = "CARPOOL",
  POLL = "POLL",
  GALLERY = "GALLERY",
  WEATHER = "WEATHER",
  COMMENT_SECTION = "COMMENT_SECTION"
}
