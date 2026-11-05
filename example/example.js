// "This class was written at 2am. You can tell."

class Developer {
    constructor({ location, birthDate, deathDate = null, weaknesses = [] }) {
        this.location = location;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.weaknesses = weaknesses;
        this.imposterSyndrome = true;
        this.coffeeDependency = 'chronic';
    }

    get age() {
        const now = this.deathDate || new Date().getFullYear();
        const years = now - this.birthDate;
        return years > 0
            ? `${years} years of regret (and ${Math.floor(years * 250)} lines of "it works on my machine")`
            : `Born in the future. Technically immortal. Unfortunately.`;
    }

    get realAge() {
        // Every dev is 25 in their head, 45 in their back
        return 25;
    }

    describe() {
        const roast = this.weaknesses
            .map(w => `  • ${w}`)
            .join('\n');
        return `
📍 ${this.location}
 Age: ${this.age}
 Status: ${this.deathDate ? 'Deceased (RIP)' : 'Alive (barely)'}
 Coffee: ${this.coffeeDependency}
 Imposter Syndrome: ${this.imposterSyndrome ? 'MAX' : 'still here'}

Known Weaknesses:
${roast}

Self-Note: "I'll refactor this later." (You won't.)
        `;
    }

    fixBug(bug) {
        return `Fixed ${bug}. Broke 3 other things. Net progress: -2. Ship it.`;
    }

    deploy() {
        return this.deathDate
            ? "Can't deploy. I'm dead. Try `git push` from the afterlife."
            : "Deployed to prod. Praying it doesn't catch fire. 🙏";
    }
}

const Dumb = new Developer({
    location: 'San Francisco, CA',
    birthDate: 2026,
    deathDate: 0,
    weaknesses: [
        "Can't code without Claude",
        "Overthinker",
        "Named a class `Bob` then instantiated `Guy`",
        "Uses `var` in secret"
    ]
});

console.log(Dumb.describe());
console.log(Dumb.fixBug('off-by-one error'));
console.log(Dumb.deploy());   