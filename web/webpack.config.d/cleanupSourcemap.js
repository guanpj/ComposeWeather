// Replace paths unavailable during compilation with `null`, so they will not be shown in devtools
;
(() => {
    try {
        const fs = require("fs");
        const path = require("path");

        const outDir = __dirname + "/kotlin/"
        const projectName = path.basename(__dirname);
        const mapFile = outDir + projectName + ".map"

        // Check if map file exists before trying to read it
        if (!fs.existsSync(mapFile)) {
            console.log("Source map file not found, skipping cleanup: " + mapFile);
            return;
        }

        const sourcemap = JSON.parse(fs.readFileSync(mapFile))
        const sources = sourcemap["sources"]
        srcLoop: for (let i in sources) {
            const srcFilePath = sources[i];
            if (srcFilePath == null) continue;

            const srcFileCandidates = [
                outDir + srcFilePath,
                outDir + srcFilePath.substring("../".length),
                outDir + "../" + srcFilePath,
            ];

            for (let srcFile of srcFileCandidates) {
                if (fs.existsSync(srcFile)) continue srcLoop;
            }

            sources[i] = null;
        }

        fs.writeFileSync(mapFile, JSON.stringify(sourcemap));
    } catch (e) {
        console.log("Warning: Failed to cleanup source map:", e.message);
    }
})();