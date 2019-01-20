if (process.env.NODE_ENV === "production") {
    const opt = require("./bookmarks-slinky-opt.js");
    opt.entrypoint.main();
    module.exports = opt;
} else {
    var exports = window;
    exports.require = require("./bookmarks-slinky-fastopt-entrypoint.js").require;
    window.global = window;

    const fastOpt = require("./bookmarks-slinky-fastopt.js");
    fastOpt.entrypoint.main()
    module.exports = fastOpt;

    if (module.hot) {
        module.hot.accept();
    }
}
