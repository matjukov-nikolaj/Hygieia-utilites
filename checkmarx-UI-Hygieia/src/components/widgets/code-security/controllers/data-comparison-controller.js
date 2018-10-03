class DataComparisonController{

    constructor(response, name, config) {
        this.response = response;
        this.collectorName = name;
        this.config = config;
        this.differenceOfValues = {
        }
    }

    control() {
        this._processBlockWithDifferenceValues();
    };

    getDifferenceOfValues() {
        return this.differenceOfValues;
    }

    _processBlockWithDifferenceValues() {
        let differenceBlockStyle = document.getElementById(this.collectorName + "DifferenceBlock").style;
        if (this.response.result.length === 2) {
            differenceBlockStyle.display = config.BLOCK;
            this._showDifferenceBlock(differenceBlockStyle);
        } else {
            this._hideBlock(differenceBlockStyle);
        }
    }

    _showDifferenceBlock(differenceBlockStyle) {
        differenceBlockStyle.display = config.BLOCK;
        const metricsValuesDifferences = this.response.result[1].metrics;
        const differenceOfLow = metricsValuesDifferences[this.config.LOW];
        const differenceOfMedium = metricsValuesDifferences[this.config.MEDIUM];
        const differenceOfHigh = metricsValuesDifferences[this.config.HIGH];
        this._showDifferenceOfValues(differenceOfLow, this.config.LOW);
        this._showDifferenceOfValues(differenceOfMedium, this.config.MEDIUM);
        this._showDifferenceOfValues(differenceOfHigh, this.config.HIGH);
    }

    _showDifferenceOfValues(value, element) {
        let downArrowStyle = document.getElementById(this.config.COLLECTOR + element + config.VALUES_DOWN).style;
        this._hideBlock(downArrowStyle);
        let upArrowStyle = document.getElementById(this.config.COLLECTOR + element + config.VALUES_UP).style;
        this._hideBlock(upArrowStyle);
        let blockElement = document.getElementById(config.DIFFERENCE + element + config.VALUES_BLOCK);
        if (value > 0) {
            blockElement.style.color = config.GREEN_COLOR;
            downArrowStyle.display = config.INLINE_BLOCK;
        } else if (value < 0) {
            blockElement.style.color = config.RED_COLOR;
            upArrowStyle.display = config.INLINE_BLOCK;
            this._replaceNegativeValueOfElement(element, value);
        } else {
            blockElement.style.color = config.YELLOW_COLOR;
        }
    }

    _hideBlock(element) {
        if (element.display !== config.NONE) {
            element.display = config.NONE;
        }
    }

    _replaceNegativeValueOfElement(element, value) {
        if (element === this.config.LOW) {
            this.differenceOfValues[this.config.LOW] = Math.abs(value);
        } else if (element === this.config.MEDIUM) {
            this.differenceOfValues[this.config.MEDIUM] = Math.abs(value);
        } else {
            this.differenceOfValues[this.config.HIGH] = Math.abs(value);
        }
    }
}