function getAreaGradientStyle() {
    return {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
            offset: 0,
            color: '#5e92f3'
        }, {
            offset: 1,
            color: '#003c8f'
        }])
    }
}

function getBasicDataZoom() {
    return [{
        type: 'inside',
        start: 0,
        end: 100
    },]
}

function getDataZoomWithScrubber() {
    return [{
        type: 'inside',
        start: 0,
        end: 100
    }, {}]
}

function getBasicToolbox() {
    return {
        feature: {
            restore: {},
            saveAsImage: {}
        }
    }
}

function getBasicGrid() {
    return {
        left: '3%',
        right: '3%',
        bottom: '3%',
        containLabel: true
    }
}

function getGridWithoutBottom() {
    return {
        left: '3%',
        right: '3%',
        containLabel: true
    }
}

function getBasicOptions(title) {
    return {
        toolbox: getBasicToolbox(),
        grid: getBasicGrid(),
        dataZoom: getBasicDataZoom(),
        tooltip: {},
        title: {
            text: title
        }
    }
}

function buildFromBasicOptions(chartContainerId, title, customization) {
    var result = getBasicOptions(title)
    Object.assign(result, customization)
    var chart = echarts.init(document.getElementById(chartContainerId), 'dark');
    chart.setOption(result)
}